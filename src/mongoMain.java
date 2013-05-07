import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.BSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.QueryOperators;

/*	
 * @(#)mongoMain.java     1.0 @2013-1-24		
 * 		
 * Copyright (c) 2010 SHANGHAI BINGKUN DIGITAL TECHNOLOGY CO.,LTD		
 * All rights reserved		
 * 		
 * This software is the confidential and proprietary information of 		
 * SHANGHAI BINGKUN.("Confidential Information").  You shall not		
 * disclose such Confidential Information and shall use it only in		
 * accordance with the terms of the license agreement you entered into		
 * with SHANGHAI BINGKUN.		
 */

/**
 * 
 * 测试mongoDB连接
 *
 * @author jijw
 *
 * @version  2013-1-24
 */
public class mongoMain {
	
	private static Mongo m;
	private static DB db;
	private static DBCollection coll;
	
	@SuppressWarnings("unused")
	private static void init() throws UnknownHostException, MongoException{
		m = new Mongo("127.0.0.1", 27017);
		db = m.getDB("mongoTest");
		getCollectionDB();
	}
	
	
	/**
	 * 连接账户,需要权限验证的情况下，需要以下方法
	 * @return
	 */
	private static boolean getCollectionDB(){
		
		boolean ret = false;
		
		if (db.authenticate("jijw", "jijw".toCharArray())) {
			ret = true;
			print("DB Collection Success");
		} else {
			print("DB Collection Flase");
		}
		return ret;
	}
	
	/**
	 * 查看Collection下的所有document
	 * @param colName
	 * @throws MongoException 
	 * @throws UnknownHostException 
	 */
	private static void queryAll(String collName) throws UnknownHostException, MongoException{
		init();
		
		// 显示mongoTest数据库下的t_1集合的documents
//		DBCollection dbColl = db.getCollection("t_1");
		coll = db.getCollection(collName);
		
		// 查询collection下所有的数据
		print("查询collection下所有的数据: ");
		
		// DB 游标
		DBCursor cur = coll.find();
		
		while(cur.hasNext()){
			print(cur.next());
		}
		
		print("count: " + coll.count() );
	}
	private static void queryAll2(String collName) throws UnknownHostException, MongoException{
		init();
		
		// 显示mongoTest数据库下的t_1集合的documents
//		DBCollection dbColl = db.getCollection("t_1");
		coll = db.getCollection(collName);
		
		// 查询collection下所有的数据
		print("查询collection下所有的数据: ");
		
		// DB 游标
		DBCursor cur = coll.find().sort(new BasicDBObject("age",1));
		
		while(cur.hasNext()){
			print(cur.next());
		}
		
		print("count: " + coll.count() );
	}
	
	/**
	 * 显示数据库下的集合
	 */
	private static void queryColName(){
		// 显示数据库下的集合
		for(String collectionName : db.getCollectionNames()){
			System.out.println("collectionName: " + collectionName);
		}
		print(null);
	}
	
	/**
	 * 添加数据
	 * @throws MongoException 
	 * @throws UnknownHostException 
	 */
	public static void add() throws UnknownHostException, MongoException{
		// 先查询所有数据
		queryAll("t_1");
		
		DBObject user = new BasicDBObject();
		
		user.put("name", "machineArr1");
		user.put("age", 11);
		
		user.put("sex", "oth");
		
		// 新增单个doc
//		coll.save(user);
		
		// 新增单个doc
//		coll.insert(user);

		// 新增数组多个doc 方式一
//		DBObject [] dboArr = {user,new BasicDBObject("name","machineArr2"),new BasicDBObject("name","machineArr3")};
//		coll.insert(dboArr);
		
		// 新增数组多个doc 方式二
//		coll.insert(user,new BasicDBObject("name","tom5.1") ,new BasicDBObject("name","tom5.2"));
		
		// 添加list 集合
//		List<DBObject> list = new ArrayList<DBObject>();
//		list.add(user);
//		DBObject user2 = new BasicDBObject();
//		user2.put("name", "machineList2");
//		user2.put("age", 22);
//		list.add(user2);
//		
//		DBObject user3 = new BasicDBObject();
//		user3.put("name", "machineList3");
//		user3.put("age", 33);
//		list.add(user3);
//		
//		coll.insert(list);
		
		queryAll("t_1");
	}
	
	/**
	 * 清除数据
	 * @throws UnknownHostException
	 * @throws MongoException
	 */
	private static void remove() throws UnknownHostException, MongoException{
		queryAll("t_1");
		
		// 清除name为 tom4.2的数据
		print(coll.remove(new BasicDBObject("name","tom4.2")).getN());
		
		// 清除age不小于88的数据
		print("removeage>=88: "+coll.remove(new BasicDBObject("age",new BasicDBObject("$gte",88))).getN());
		
		queryAll("t_1");
	} 
	
	/**
	 * 修改数据
	 * @throws MongoException 
	 * @throws UnknownHostException 
	 */
	private static void modify() throws UnknownHostException, MongoException{
		queryAll("t_1");
//		print("修改name=jijw age=25" + coll.update(new BasicDBObject("name","jijw"), new BasicDBObject("age",25)).getN());
//		print("修改name=jijw age=25" + coll.update(new BasicDBObject("name","jijw"), new BasicDBObject("age",25),true,false).getN());
		// 如果不用set就是把原来的对象更新为现在的对象
		
		DBObject query = new BasicDBObject();
		DBObject fieldQuery = new BasicDBObject();
		fieldQuery.put("age", 24);
		fieldQuery.put("remark", "修改两个");
		query.put("$set", fieldQuery);
		
// 		print("修改name=jijw age=25" + coll.update(new BasicDBObject("name","jijw"), new BasicDBObject("$set",new BasicDBObject("age",25)),true,false).getN());
 		print("修改name=jijw age=25 remark=修改两个" + coll.update(new BasicDBObject("name","jijw"), query,true,false).getN());
		
//		print("修改："+coll.update(new BasicDBObject("name","jijw"),new BasicDBObject("name","dingding"),true,true).getN());		
		// 修改collection的所有数据的status属性
//		coll.update(new BasicDBObject(), new BasicDBObject("$set",new BasicDBObject("status","0")),true,false);
		queryAll("t_1");
		
	}
	
	/**
	 * 查询
	 * @throws UnknownHostException
	 * @throws MongoException
	 */
	private static void query() throws UnknownHostException, MongoException{
		queryAll("t_1");
		
		// 查询单条
//		print("查询单条，有多条只显示一条:  " + coll.findOne(new BasicDBObject("name","jijw")));
//		
//		print("查询单条，有多条只显示一条，结果为游标:  " + coll.find(new BasicDBObject("name","jijw")));
//		// 查询多条，结果为数组
//		print("查询数组: " + coll.find(new BasicDBObject("sex","oth")).toArray());
//		print("查询数组长度： " + coll.find(new BasicDBObject("sex","oth")).count());
//		print("age in 1/2/3: " +coll.find(new BasicDBObject("age",new BasicDBObject(QueryOperators.IN, new int[]{1,2,3}))).toArray());
//		print("age in 1/2/3 只显示field为name的数据: " +coll.find(new BasicDBObject("age",new BasicDBObject(QueryOperators.IN, new int[]{1,2,3})),new BasicDBObject("name","1")).toArray());
//		print("查询field具有remark的数据: " +coll.find(new BasicDBObject("age",new BasicDBObject(QueryOperators.EXISTS, true))).toArray());
//		print("只查询age属性"+ coll.find(null, new BasicDBObject("age",true)).toArray());
//		print("用age排序： " + coll.find().sort(new BasicDBObject("age",1)).toArray());
//		print(coll.find(new BasicDBObject("year",null)).toArray());
		
		// age 大于11
//		print("11 < age ：" +　coll.find(new BasicDBObject("age",new BasicDBObject("$gt",11))).toArray());
		// age大于11且小于24
//		DBObject ss = new BasicDBObject();
//		ss.put("$gt", 11);
//		ss.put("$lt", 24);
//		print("11 < age < 24 ：" + coll.find(new BasicDBObject("age",ss)).toArray());
		
//		queryAll2("t_1");
		// 属性有year且year值为2013、2012的数据
//		DBObject query = new BasicDBObject();
//		query.put(QueryOperators.EXISTS, true);
//		query.put(QueryOperators.IN, new int [] {2013,2012});
//		print(coll.find(new BasicDBObject("year",query)).toArray());
		
		// 查询name 、age两个属性
//		DBObject query = new BasicDBObject();
//		query.put("name", 1);
//		query.put("age", 1);
//		print(coll.find(null,query).toArray());
		
	}
	
	public static void main(String[] args) throws Exception {
//		add();
//		remove();
		modify();
//		query();
		
	}
	
	private static void print(Object o){
		System.out.println(o);
	}
	
	
}
