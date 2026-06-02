package com.microinfra.uid.worker;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microinfra.commons.lang.NetworkUtils;
import com.microinfra.uid.entity.WorkerNode;

@Service("defaultWorkerIdAssigner")
public class DefaultWorkerIdAssigner implements WorkerIdAssigner {

	@Resource
	private WorkerNodeService workerNodeService;

	@Transactional
	public long assignWorkerId() {
		WorkerNode workerNode = buildWorkerNode();
		workerNodeService.save(workerNode);

		return workerNode.getId();
	}

	private WorkerNode buildWorkerNode() {
		Date now = new Date();
		WorkerNode workerNode = new WorkerNode();
		workerNode.setHost(NetworkUtils.getLocalAddress());
		workerNode.setPort(System.currentTimeMillis() + "-" + RandomUtils.nextInt(1000, 100000));
		workerNode.setLaunchTime(now);
		workerNode.setCreateTime(now);
		workerNode.setUpdateTime(now);

		return workerNode;
	}

}