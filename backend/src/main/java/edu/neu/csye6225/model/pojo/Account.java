package edu.neu.csye6225.model.pojo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DynamoDBTable(tableName = "csye")
public class Account {
    @DynamoDBHashKey(attributeName="username")
    private String username;
    @DynamoDBRangeKey(attributeName="token")
    private String token;
    private String TimeToLive;
}
