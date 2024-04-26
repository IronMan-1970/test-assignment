package org.antihetman.testassignment.controller;/*
  @author   antihetman
  @project   test-assignment
  @class  UserErrorResponse
  @version  1.0.0 
  @since 4/26/24 - 22.59
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}
