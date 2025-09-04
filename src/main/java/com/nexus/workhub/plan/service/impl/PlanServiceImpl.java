package com.nexus.workhub.plan.service.impl;

import org.springframework.stereotype.Service;

import com.nexus.workhub.common.util.JNAUtil;
import com.nexus.workhub.plan.dto.PlanCreateVO;
import com.nexus.workhub.plan.dto.PlanUpdateVO;
import com.nexus.workhub.plan.service.PlanService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaodong1.li
 * @date
 */
@Slf4j
@Service
public class PlanServiceImpl implements PlanService {

    @Override
    public String importPlan(byte[] file) {

        // 将一个gzp文件中的json提取出来，插图和附件会舍弃。
        String gzpJsonStr = JNAUtil.extractFromGzp(file);

        log.info("importPlan rsp gzpJsonStr: {}", gzpJsonStr);

        // TODO 将解析后的数据分别写入DB(Projects, Works, Dependencies)

        String projectId = "project-I-" + System.currentTimeMillis();

        return projectId;
    }

    @Override
    public String createPlan(PlanCreateVO vo) {

        // 创建一个空的全新的斑马文件
        String result = JNAUtil.createBlankDoc();

        log.info("createPlan rsp: {}", result);

        // TODO 将解析后的数据分别写入DB(Projects, Works, Dependencies)

        String projectId = "project-C-" + System.currentTimeMillis();

        return projectId;
    }

    @Override
    public String viewPlan(String projectId) {

        // 1、 查询数据库：后端接收请求后，根据 project_id 从数据库中查询 Projects 表。

        // 2、获取完整数据：通过 project_id 作为外键，联表查询或多次查询 Works, Dependencies, Work_Resources
        // 等所有相关联的表，将一个计划的所有数据从数据库中读取出来。

        // 3、组合为JSON：将查询出的所有数据，按照斑马标准JSON格式的结构，重新组装成一个完整的JSON对象。

        // 4、返回响应：将组装好的JSON数据返回给前端，前端的WebAssembly插件即可直接渲染展示。

        // 注意：文档提到动态库的每次接口调用都需要传入完整的计划JSON。这意味着，后端如果想使用 GetTotalInfo
        // 等接口，需要先从数据库中获取数据并组装成JSON，再传入动态库。

        // TODO 1. 根据projectId从DB中获取项目信息，转换成gzp文件
        // TODO 2. 将gzp文件转换成json字符串

        String gzpJsonStr = "";

        log.info("viewPlan rsp gzpJsonStr: {}", gzpJsonStr);

        return gzpJsonStr;
    }

    @Override
    public String updatePlan(String projectId, PlanUpdateVO vo) {

        // 根据 project_id，从数据库中查询并组装当前计划的完整JSON数据。

        // SetWorksStatusInfo()：将前端的变更数据（如 {work_id: xx, progress:
        // 80}）和完整的计划JSON，一并传入此接口。

        // SetProgressStr()：当用户设置前锋线时，传入前锋线时间点和完整的计划JSON。

        // 处理计算结果：动态库会返回一个经过重新计算的新的完整计划JSON

        // 更新数据库：后端解析这个新的JSON，并更新数据库中对应的表记录（Works 表中的进度、开始/结束日期等字段）

        String gzpJsonStr = "";

        log.info("updatePlan rsp gzpJsonStr: {}", gzpJsonStr);

        return gzpJsonStr;
    }

}
