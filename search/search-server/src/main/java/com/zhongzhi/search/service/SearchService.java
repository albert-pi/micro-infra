package com.zhongzhi.search.service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.SearchResult;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

@Service
public class SearchService {
	private final String INDEX_PATH = "lucene_index";
	private final Analyzer analyzer = new IKAnalyzer();

	public void createIndex(Article article) {
		try (Directory directory = FSDirectory.open(Paths.get(INDEX_PATH));
				IndexWriterConfig config = new IndexWriterConfig(analyzer);
				IndexWriter writer = new IndexWriter(directory, config)) {

			Document doc = new Document();
			doc.add(new Field("id", article.getId().toString(), FIELD_STRATEGY.get("id")));
			doc.add(new Field("title", article.getTitle(), FIELD_STRATEGY.get("title")));
			doc.add(new Field("content", article.getContent(), FIELD_STRATEGY.get("content")));

			writer.addDocument(doc);
		} catch (IOException e) {
			throw new LuceneException("索引创建失败", e);
		}
	}

	// 分页搜索（含高亮）
	public SearchResult search(String keyword, int page, int size) {
		try (Directory directory = FSDirectory.open(Paths.get(INDEX_PATH));
				IndexReader reader = DirectoryReader.open(directory);
				IndexSearcher searcher = new IndexSearcher(reader)) {

			// 构建查询
			QueryParser parser = new QueryParser("content", analyzer);
			Query query = parser.parse(QueryParser.escape(keyword));

			// 高亮设置
			Highlighter highlighter = new Highlighter(new SimpleHTMLFormatter("<span class='hl'>", "</span>"), new QueryScorer(query));
			highlighter.setTextFragmenter(new SimpleFragmenter(100)); // 片段长度100字

			// 执行搜索
			TopDocs topDocs = searcher.search(query, page * size);
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;

			List<SearchResult.Item> items = new ArrayList<>();
			for (int i = (page - 1) * size; i < scoreDocs.length; i++) {
				Document doc = searcher.doc(scoreDocs[i].doc);
				String content = highlighter.getBestFragment(analyzer, "content", doc.get("content"));

				items.add(new SearchResult.Item(Long.parseLong(doc.get("id")), doc.get("title"),
						content != null ? content : doc.get("content").substring(0, 100)));
			}

			return new SearchResult(topDocs.totalHits.value, items);
		} catch (IOException | ParseException e) {
			throw new LuceneException("搜索失败", e);
		}
	}
}