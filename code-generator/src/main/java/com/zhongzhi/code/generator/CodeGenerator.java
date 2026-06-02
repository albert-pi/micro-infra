package com.zhongzhi.code.generator;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

@SpringBootApplication
public class CodeGenerator implements CommandLineRunner {

	public static void main(String[] args) {
		new SpringApplicationBuilder().sources(CodeGenerator.class).web(WebApplicationType.NONE).run(args);
	}

	@Resource
	private Config config;

	@Override
	public void run(String... args) throws Exception {
		GlobalConfig.Builder gcBuilder = new GlobalConfig.Builder();
		GlobalConfig gc = gcBuilder.outputDir(config.getCodeOutputDir() + "/src/main/java").author("albert pi").openDir(false).commentDate("yyyy-MM-dd HH:mm:ss").fileOverride()
				.build();

		DataSourceConfig.Builder dscBuilder = new DataSourceConfig.Builder(config.getDsUrl(), config.getUserName(), config.getPassword());
		DataSourceConfig dsc = dscBuilder.build();

		PackageConfig.Builder pcBuilder = new PackageConfig.Builder();
		pcBuilder.parent("com.zhongzhi").moduleName(config.getModule()).mapper("mapper").entity("entity").service("service").serviceImpl("service");
		// .xml("xml") xml无效 putPathInfo(templateConfig.getXml(), ConstVal.XML_PATH,
		// ConstVal.MAPPER);

		PackageConfig pc = pcBuilder.build();

		StrategyConfig.Builder scBuilder = new StrategyConfig.Builder();
		String[] tables = config.getTables().split(",");
		StrategyConfig sc = scBuilder.enableCapitalMode().enableSkipView().addInclude(tables).entityBuilder().enableLombok().naming(NamingStrategy.underline_to_camel)
				.columnNaming(NamingStrategy.underline_to_camel).enableSerialVersionUID().build();

		TemplateConfig.Builder tcBuilder = new TemplateConfig.Builder();
		// 不要生成controller和xml。不能禁止生成service接口：... case SERVICE: this.service = null;
		// this.serviceImpl = null; break;
		TemplateConfig tc = tcBuilder.disable(TemplateType.CONTROLLER, TemplateType.XML).build();

		AutoGenerator generator = new AutoGenerator(dsc);
		generator.global(gc);
		generator.packageInfo(pc);
		generator.strategy(sc);
		generator.template(tc);

		generator.execute();
	}

}