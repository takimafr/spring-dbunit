/*
 * Copyright (c) 2001-2014 Group JCDecaux. 
 * 17 rue Soyer, 92523 Neuilly Cedex, France.
 * All rights reserved. 
 * 
 * This software is the confidential and proprietary information
 * of Group JCDecaux ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you
 * entered into with Group JCDecaux.
 */

package com.excilys.ebi.spring.dbunit.dataset;

public interface DataSetDecorator {
    String getStringToReplace();
    
    String getStringReplacement();
}
