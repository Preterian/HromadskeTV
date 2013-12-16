package com.leoart.hromadske.orm;


import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import com.leoart.hromadske.model.Post;

public class DataBaseConfigUtil extends OrmLiteConfigUtil {
	private static final Class<?>[] classes = new Class[] { Post.class };

	public static void main(String[] args) throws Exception {
		writeConfigFile("ormlite_config.txt", classes);
	}
}
