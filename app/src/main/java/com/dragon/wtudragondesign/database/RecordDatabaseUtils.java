package com.dragon.wtudragondesign.database;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RecordDatabaseUtils {
	static RecordSQLiteOpenHelper helper;
	static SQLiteDatabase db;

	/* 插入数据 */
	public static void insertData(String tempName, Context context) {
		helper = new RecordSQLiteOpenHelper(context);
		db = helper.getWritableDatabase();
		db.execSQL("insert into records(name) values('" + tempName + "')");
		db.close();
	}

	/* 检查数据库中是否已经有该条记录 */
	public static boolean hasData(String tempName, Context context) {
		helper = new RecordSQLiteOpenHelper(context);
		// 从Record这个表里找到name=tempName的id
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select id as _id,name from records where name =?",
				new String[] { tempName });
		// 判断是否有下一个
		return cursor.moveToNext();
	}

	/* 清空数据 */
	public static void deleteData() {
		db = helper.getWritableDatabase();
		db.execSQL("delete from records");
		db.close();
	}

	/* 模糊查询数据 并显示在RecycleView列表上 */
	public static List<String> queryData(Context context) {
		List<String> list = new ArrayList<>();
		// 模糊搜索
		helper = new RecordSQLiteOpenHelper(context);
		db = helper.getWritableDatabase();
		Cursor cursor = db.query("records", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				String hiString = cursor.getString(cursor
						.getColumnIndex("name"));
				list.add(hiString);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return list;
	}
}
