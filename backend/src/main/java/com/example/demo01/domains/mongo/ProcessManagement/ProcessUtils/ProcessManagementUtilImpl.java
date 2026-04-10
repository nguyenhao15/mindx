package com.example.demo01.domains.mongo.ProcessManagement.ProcessUtils;

import com.example.demo01.configs.SecureUtil.SecurityRepoUtilImpl;
import com.example.demo01.domains.mongo.HRManagment.Department.dto.WorkingField.WorkingFieldUpdate;
import com.example.demo01.domains.mongo.HRManagment.Department.model.DepartmentModel;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessFlow.model.ACCESSPOLICY;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessFlow.model.ProcessFlow;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.dtos.processTag.ProcessTagUpdateRecord;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.dtos.processValue.ProcessTagValueUpdateRecord;
import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.models.ProcessTagValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProcessManagementUtilImpl implements ProcessManagementUtil {

    @Autowired
    private SecurityRepoUtilImpl securityRepoUtil;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Criteria buildAccessQuery() {
        String userId = securityRepoUtil.getCurrentUserId();

        Criteria publicCriteria = Criteria.where("accessRule.accessType").is(ACCESSPOLICY.PUBLIC);

        Criteria individualCriteria = Criteria.where("accessRule.allowedUserIds").is(userId);
        Criteria byAuthor = Criteria.where("createdBy").is(userId);
        Criteria notSetCriteria = new Criteria().orOperator(
                Criteria.where("accessRule").is(null),
                Criteria.where("accessRule").exists(false)
        );

        Criteria ruleCriteria = buildRestrictlyCriteria();
        Criteria loosenCriteria = buildLoosenCriteria();

        return new Criteria().orOperator(notSetCriteria, publicCriteria, individualCriteria, ruleCriteria, loosenCriteria, byAuthor);
    }


    @Override
    public Criteria buildRestrictlyCriteria() {
        List<String> departmentIds = securityRepoUtil.getCurrentDepartmentIds();
        List<String> positionIds = securityRepoUtil.getCurrentPositionIds();
        List<String> buIds = securityRepoUtil.getCurrentUserBuIds();
        int viewLevel = securityRepoUtil.getViewLevel();

        List<Criteria> restrictionList = new ArrayList<>();

        Criteria restrictedBase = Criteria.where("accessRule.accessType").is(ACCESSPOLICY.RESTRICTED);

        restrictionList.add(new Criteria().orOperator(
                    Criteria.where("accessRule.departmentCodes").in(departmentIds),
                    Criteria.where("accessRule.departmentCodes").size(0),
                    Criteria.where("accessRule.departmentCodes").exists(false)
            ));

        restrictionList.add(new Criteria().orOperator(
                    Criteria.where("accessRule.positionCodes").in(positionIds),
                    Criteria.where("accessRule.positionCodes").size(0),
                    Criteria.where("accessRule.positionCodes").exists(false)
            ));


        restrictionList.add(new Criteria().orOperator(
                    Criteria.where("accessRule.buIds").in(buIds),
                    Criteria.where("accessRule.buIds").size(0),
                    Criteria.where("accessRule.buIds").exists(false)
            ));

        restrictionList.add(new Criteria().orOperator(
                Criteria.where("accessRule.minLevel").lte(viewLevel),
                Criteria.where("accessRule.minLevel").is(0),
                Criteria.where("accessRule.minLevel").exists(false)
        ));


        return new Criteria().andOperator(
                restrictedBase,
                new Criteria().andOperator(restrictionList.toArray(new Criteria[0]))
        );
    }


    @Override
    public Criteria buildLoosenCriteria() {
        List<String> departmentIds = securityRepoUtil.getCurrentDepartmentIds();
        List<String> positionIds = securityRepoUtil.getCurrentPositionIds();
        List<String> buIds = securityRepoUtil.getCurrentUserBuIds();
        int viewLevel = securityRepoUtil.getViewLevel();

        List<Criteria> restrictionList = new ArrayList<>();

        Criteria restrictedBase = Criteria.where("accessRule.accessType").is(ACCESSPOLICY.LOOSEN);

        restrictionList.add(new Criteria().orOperator(
                Criteria.where("accessRule.departmentCodes").in(departmentIds),
                Criteria.where("accessRule.departmentCodes").size(0),
                Criteria.where("accessRule.departmentCodes").exists(false)
        ));

        restrictionList.add(new Criteria().orOperator(
                Criteria.where("accessRule.positionCodes").in(positionIds),
                Criteria.where("accessRule.positionCodes").size(0),
                Criteria.where("accessRule.positionCodes").exists(false)
        ));


        restrictionList.add(new Criteria().orOperator(
                Criteria.where("accessRule.buIds").in(buIds),
                Criteria.where("accessRule.buIds").size(0),
                Criteria.where("accessRule.buIds").exists(false)
        ));

        restrictionList.add(new Criteria().orOperator(
                Criteria.where("accessRule.minLevel").lte(viewLevel),
                Criteria.where("accessRule.minLevel").is(0),
                Criteria.where("accessRule.minLevel").exists(false)
        ));


        return new Criteria().andOperator(
                restrictedBase,
                new Criteria().orOperator(restrictionList.toArray(new Criteria[0]))
        );
    }

    @Override
    public void handleProcessTagUpdate(ProcessTagUpdateRecord updateRecord) {
        try {
            Query queryInProcessTagValue = new Query();
            queryInProcessTagValue.addCriteria(Criteria.where("tagItems._id").is(updateRecord.id()));
            Update updateProcessTagValue = new Update()
                    .set("tagItems.$.fullTagName", updateRecord.fullTagName())
                    .set("tagItems.$.tagName", updateRecord.tagName());


            Query queryProcessItem = new Query();
            queryProcessItem.addCriteria(Criteria.where("tagItems._id").is(updateRecord.id()));
            Update updateProcessItem = new Update()
                    .set("tagItems.$.fullTagName", updateRecord.fullTagName())
                    .set("tagItems.$.tagName", updateRecord.tagName());

            mongoTemplate.updateMulti(queryProcessItem, updateProcessItem, ProcessFlow.class);
            mongoTemplate.updateMulti(queryInProcessTagValue, updateProcessTagValue, ProcessTagValue.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update departments when working field updated: " + e.getMessage(), e);
        }
    }

    @Override
    public void handleTagValueUpdate(ProcessTagValueUpdateRecord updateRecord) {
        try {
            Query queryInProcessTagValue = new Query();
            queryInProcessTagValue.addCriteria(Criteria.where("tagIdValues._id").is(updateRecord.id()));
            Update updateProcessTagValue = new Update()
                    .set("tagIdValues.$.tagTitle", updateRecord.tagTitle())
                    .set("tagIdValues.$.tagValueCode", updateRecord.tagValueCode());

            mongoTemplate.updateMulti(queryInProcessTagValue, updateProcessTagValue, ProcessFlow.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update departments when working field updated: " + e.getMessage(), e);
        }
    }

    @Override
    public void handleUpdateWorkingField(WorkingFieldUpdate workingFieldUpdate) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("workingFields.workingFieldCode").is(workingFieldUpdate.fieldCode()));
            Update update = new Update()
                    .set("workingFields.$.workingFieldCode", workingFieldUpdate.fieldCode())
                    .set("workingFields.$.workingFieldName", workingFieldUpdate.fieldName());
            mongoTemplate.updateMulti(query, update, DepartmentModel.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update departments when working field updated: " + e.getMessage(), e);
        }
    }

}
