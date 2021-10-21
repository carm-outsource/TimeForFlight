package cc.carm.plugin.timeforflight.managers;

import cc.carm.plugin.timeforflight.models.UserData;
import cc.carm.plugin.timeforflight.Main;

import java.io.File;
import java.util.*;

public class DataManager {
	private static File userdataFolder;

	/**
	 * 通过这个Map缓存玩家的数据
	 */
	public static Map<UUID, UserData> userDataCaches = new HashMap<>();

	public static void init() {
		userdataFolder = new File(Main.getInstance().getDataFolder() + File.separator + "userdata");
		if (!userdataFolder.isDirectory() || !userdataFolder.exists()) {
			userdataFolder.mkdir();
		}
	}

	public static UserData loadData(UUID uuid) {
		UserData prefixCache = new UserData(uuid);

		userDataCaches.put(uuid, prefixCache);

		return prefixCache;
	}


	public static UserData getData(UUID uuid) {
		return userDataCaches.getOrDefault(uuid, loadData(uuid));
	}

	public static void unloadData(UUID uuid) {
		if (isDataLoaded(uuid)) {
			UserData data = getData(uuid);
			data.stopTasks();
			data.saveData();

			userDataCaches.remove(uuid);
		}
	}


	public static boolean isDataLoaded(UUID uuid) {
		return userDataCaches.containsKey(uuid);
	}

	/**
	 * 判断一个UUID是否有已保存的数据
	 *
	 * @param uuid 判断的UUID
	 * @return 是否已有数据
	 */
	public static boolean hasData(UUID uuid) {
		return Arrays.stream(Objects.requireNonNull(userdataFolder.listFiles()))
				.anyMatch(file -> file.getName().startsWith(uuid.toString()));
	}

}
