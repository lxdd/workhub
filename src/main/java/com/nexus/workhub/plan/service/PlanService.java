package com.nexus.workhub.plan.service;

import com.nexus.workhub.plan.dto.PlanCreateVO;
import com.nexus.workhub.plan.dto.PlanUpdateVO;

/**
 * @author xiaodong1.li
 * @date
 */
public interface PlanService {

    /**
     * 
     * @param file
     * @return
     */
    String importPlan(byte[] file);

    /**
     * 
     * @param vo
     * @return
     */
    String createPlan(PlanCreateVO vo);

        /**
     * 
     * @param vo
     * @return
     */
    String updatePlan(String projectId, PlanUpdateVO vo);

    /**
     * 
     * @param projectId
     * @return
     */
    String viewPlan(String projectId);
}
