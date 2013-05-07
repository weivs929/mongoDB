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
 * ����mongoDB����
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
	 * �����˻�,��ҪȨ����֤������£���Ҫ���·���
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
	 * �鿴Collection�µ�����document
	 * @param colName
	 * @throws MongoException 
	 * @throws UnknownHostException 
	 */
	private static void queryAll(String collName) throws UnknownHostException, MongoException{
		init();
		
		// ��ʾmongoTest���ݿ��µ�t_1���ϵ�documents
//		DBCollection dbColl = db.getCollection("t_1");
		coll = db.getCollection(collName);
		
		// ��ѯcollection�����е�����
		print("��ѯcollection�����е�����: ");
		
		// DB �α�
		DBCursor cur = coll.find();
		
		while(cur.hasNext()){
			print(cur.next());
		}
		
		print("count: " + coll.count() );
	}
	private static void queryAll2(String collName) throws UnknownHostException, MongoException{
		init();
		
		// ��ʾmongoTest���ݿ��µ�t_1���ϵ�documents
//		DBCollection dbColl = db.getCollection("t_1");
		coll = db.getCollection(collName);
		
		// ��ѯcollection�����е�����
		print("��ѯcollection�����е�����: ");
		
		// DB �α�
		DBCursor cur = coll.find().sort(new BasicDBObject("age",1));
		
		while(cur.hasNext()){
			print(cur.next());
		}
		
		print("count: " + coll.count() );
	}
	
	/**
	 * ��ʾ���ݿ��µļ���
	 */
	private static void queryColName(){
		// ��ʾ���ݿ��µļ���
		for(String collectionName : db.getCollectionNames()){
			System.out.println("collectionName: " + collectionName);
		}
		print(null);
	}
	
	/**
	 * �������
	 * @throws MongoException 
	 * @throws UnknownHostException 
	 */
	public static void add() throws UnknownHostException, MongoException{
		// �Ȳ�ѯ��������
		queryAll("t_1");
		
		DBObject user = new BasicDBObject();
		
		user.put("name", "machineArr1");
		user.put("age", 11);
		
		user.put("sex", "oth");
		
		// ��������doc
//		coll.save(user);
		
		// ��������doc
//		coll.insert(user);

		// ����������doc ��ʽһ
//		DBObject [] dboArr = {user,new BasicDBObject("name","machineArr2"),new BasicDBObject("name","machineArr3")};
//		coll.insert(dboArr);
		
		// ����������doc ��ʽ��
//		coll.insert(user,new BasicDBObject("name","tom5.1") ,new BasicDBObject("name","tom5.2"));
		
		// ���list ����
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
	 * �������
	 * @throws UnknownHostException
	 * @throws MongoException
	 */
	private static void remove() throws UnknownHostException, MongoException{
		queryAll("t_1");
		
		// ���nameΪ tom4.2������
		print(coll.remove(new BasicDBObject("name","tom4.2")).getN());
		
		// ���age��С��88������
		print("removeage>=88: "+coll.remove(new BasicDBObject("age",new BasicDBObject("$gte",88))).getN());
		
		queryAll("t_1");
	} 
	
	/**
	 * �޸�����
	 * @throws MongoException 
	 * @throws UnknownHostException 
	 */
	private static void modify() throws UnknownHostException, MongoException{
		queryAll("t_1");
//		print("�޸�name=jijw age=25" + coll.update(new BasicDBObject("name","jijw"), new BasicDBObject("age",25)).getN());
//		print("�޸�name=jijw age=25" + coll.update(new BasicDBObject("name","jijw"), new BasicDBObject("age",25),true,false).getN());
		// �������set���ǰ�ԭ���Ķ������Ϊ���ڵĶ���
		
		DBObject query = new BasicDBObject();
		DBObject fieldQuery = new BasicDBObject();
		fieldQuery.put("age", 24);
		fieldQuery.put("remark", "�޸�����");
		query.put("$set", fieldQuery);
		
// 		print("�޸�name=jijw age=25" + coll.update(new BasicDBObject("name","jijw"), new BasicDBObject("$set",new BasicDBObject("age",25)),true,false).getN());
 		print("�޸�name=jijw age=25 remark=�޸�����" + coll.update(new BasicDBObject("name","jijw"), query,true,false).getN());
		
//		print("�޸ģ�"+coll.update(new BasicDBObject("name","jijw"),new BasicDBObject("name","dingding"),true,true).getN());		
		// �޸�collection���������ݵ�status����
//		coll.update(new BasicDBObject(), new BasicDBObject("$set",new BasicDBObject("status","0")),true,false);
		queryAll("t_1");
		
	}
	
	/**
	 * ��ѯ
	 * @throws UnknownHostException
	 * @throws MongoException
	 */
	private static void query() throws UnknownHostException, MongoException{
		queryAll("t_1");
		
		// ��ѯ����
//		print("��ѯ�������ж���ֻ��ʾһ��:  " + coll.findOne(new BasicDBObject("name","jijw")));
//		
//		print("��ѯ�������ж���ֻ��ʾһ�������Ϊ�α�:  " + coll.find(new BasicDBObject("name","jijw")));
//		// ��ѯ���������Ϊ����
//		print("��ѯ����: " + coll.find(new BasicDBObject("sex","oth")).toArray());
//		print("��ѯ���鳤�ȣ� " + coll.find(new BasicDBObject("sex","oth")).count());
//		print("age in 1/2/3: " +coll.find(new BasicDBObject("age",new BasicDBObject(QueryOperators.IN, new int[]{1,2,3}))).toArray());
//		print("age in 1/2/3 ֻ��ʾfieldΪname������: " +coll.find(new BasicDBObject("age",new BasicDBObject(QueryOperators.IN, new int[]{1,2,3})),new BasicDBObject("name","1")).toArray());
//		print("��ѯfield����remark������: " +coll.find(new BasicDBObject("age",new BasicDBObject(QueryOperators.EXISTS, true))).toArray());
//		print("ֻ��ѯage����"+ coll.find(null, new BasicDBObject("age",true)).toArray());
//		print("��age���� " + coll.find().sort(new BasicDBObject("age",1)).toArray());
//		print(coll.find(new BasicDBObject("year",null)).toArray());
		
		// age ����11
//		print("11 < age ��" +��coll.find(new BasicDBObject("age",new BasicDBObject("$gt",11))).toArray());
		// age����11��С��24
//		DBObject ss = new BasicDBObject();
//		ss.put("$gt", 11);
//		ss.put("$lt", 24);
//		print("11 < age < 24 ��" + coll.find(new BasicDBObject("age",ss)).toArray());
		
//		queryAll2("t_1");
		// ������year��yearֵΪ2013��2012������
//		DBObject query = new BasicDBObject();
//		query.put(QueryOperators.EXISTS, true);
//		query.put(QueryOperators.IN, new int [] {2013,2012});
//		print(coll.find(new BasicDBObject("year",query)).toArray());
		
		// ��ѯname ��age��������
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
