package br.com.ead.sales.repositories;

import br.com.ead.sales.model.EventModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends ElasticsearchRepository<EventModel, String> {
    List<EventModel> findByAggregatedIdentifier(String aggregatedIdentifier);
}
