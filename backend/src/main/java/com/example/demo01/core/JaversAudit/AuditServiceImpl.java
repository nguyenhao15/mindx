// java
package com.example.demo01.core.JaversAudit;

import org.javers.core.Javers;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuditServiceImpl implements AuditService {

    private final Javers javers;

    public AuditServiceImpl(Javers javers) {
        this.javers = javers;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AuditHistoryDto> getHistory(Class<?> entityClass, String entityId) {
        QueryBuilder query = QueryBuilder.byInstanceId(entityId, entityClass)
                .withChildValueObjects()
                .limit(100);
        List<CdoSnapshot> snapshots = javers.findSnapshots(query.build());

        return snapshots.stream()
                .map(this::mapToAuditHistoryDto)
                .collect(Collectors.toList());
    }

    private AuditHistoryDto mapToAuditHistoryDto(CdoSnapshot snapshot) {
        List<String> changedFields = snapshot.getChanged();


        Map<String, Object> state = new HashMap<>();
        snapshot.getState().getPropertyNames().forEach(propName -> {
            Object value = snapshot.getPropertyValue(propName);
            state.put(propName, value);
        });

        String commitType = snapshot.getType().toString();

        return AuditHistoryDto.builder()
                .commitId(snapshot.getCommitId().toString())
                .changeDate(snapshot.getCommitMetadata().getCommitDate())
                .author(snapshot.getCommitMetadata().getAuthor())
                .version(snapshot.getVersion())
                .commitType(commitType)
                .changedFields(changedFields)
                .state(state)
                .metadata(snapshot.getCommitMetadata().getProperties())
                .build();
    }
}
