package com.carmwork.plugin.timeforflight.managers;

import com.carmwork.plugin.timeforflight.Main;
import com.carmwork.plugin.timeforflight.models.UserData;

import java.io.File;
import java.util.*;

public class DataManager {
	private static File userdatasFolder;

	/**
	 * 通过这个Map缓存玩家的数据
	 */
	public static Map<UUID, UserData> userDatas = new HashMap<>();

	public static void init() {
		userdatasFolder = new File(Main.getInstance().getDataFolder() + File.separator + "userdatas");
		if (!userdatasFolder.isDirectory() || !userdatasFolder.exists()) {
			userdatasFolder.mkdir();
		}
	}

	public static UserData loadData(UUID uuid) {
		UserData prefixCache = new UserData(uuid);

		userDatas.put(uuid, prefixCache);

		return prefixCache;
	}


	public static UserData getData(UUID uuid) {
		return userDatas.getOrDefault(uuid, loadData(uuid));
	}

	public static void unloadData(UUID uuid) {
		if (isDataLoaded(uuid)) {
			UserData data = getData(uuid);
			data.stopTasks();
			data.saveData();

			userDatas.remove(uuid);
		}
	}


	public static boolean isDataLoaded(UUID uuid) {
		return userDatas.containsKey(uuid);
	}

	/**
	 * 判断一个UUID是否有已保存的数据
	 *
	 * @param uuid 判断的UUID
	 * @return 是否已有数据
	 */
	public static boolean hasData(UUID uuid) {
		return Arrays.stream(Objects.requireNonNull(userdatasFolder.listFiles()))
				.anyMatch(file -> file.getName().startsWith(uuid.toString()));
	}

}
