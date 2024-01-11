package com.customermanagement.management.dataprofile;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customermanagement.management.model.Customer;
import com.customermanagement.management.repository.CustomerRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SparkDataProfilingService {

        @Autowired
	    private SparkSession sparkSession;

	    @Autowired
	    private CustomerRepository customerRepository;

	    public Map<String, Object> performDataProfiling() {
	        Dataset<Row> customerDataset = sparkSession.createDataFrame(customerRepository.findAll(), Customer.class);
	        Map<String, Object> profilingResults = new HashMap<>();
	        

	        // 1. Get the count of records
	        long count = customerDataset.count();
	        profilingResults.put("count", count);

	        // 2. Analyze missing values
	        Map<String, Long> missingValues = getMissingValues(customerDataset);
	        profilingResults.put("missingValues", missingValues);

	        // 3. Analyze frequency of values for 'industryType'
	        Map<String, Long> industryTypeFrequency = getFrequencyOfValues(customerDataset, "industryType");
	        profilingResults.put("industryTypeFrequency", industryTypeFrequency);

	        return profilingResults;
	    }

	    private Map<String, Long> getMissingValues(Dataset<Row> dataset) {
	        Map<String, Long> missingValues = new HashMap<>();

	        for (String columnName : dataset.columns()) {
	            long missingCount = dataset.filter(dataset.col(columnName).isNull()).count();
	            missingValues.put(columnName, missingCount);
	        }

	        return missingValues;
	    }

	    private Map<String, Long> getFrequencyOfValues(Dataset<Row> dataset, String columnName) {
	        Map<String, Long> frequencyMap = new HashMap<>();

	        Dataset<Row> frequencyData = dataset.groupBy(columnName).count();
	        List<Row> rows = frequencyData.collectAsList();

	        for (Row row : rows) {
	            String value = String.valueOf(row.getAs(columnName));
	            long frequency = row.getAs("count");
	            frequencyMap.put(value, frequency);
	        }

	        return frequencyMap;
	    }
	}

