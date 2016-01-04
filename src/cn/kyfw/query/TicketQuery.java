package cn.kyfw.query;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 余票查询
 * @author luob
 *
 */
public class TicketQuery {
	
	/**
	 * 
	 * @param date 查询日期
	 * @param from_station 起始车站代号字典
	 * @param to_station 终点车站代号字典
	 * @return
	 */
	public static String query(String date, String from_station, String to_station) {
		StringBuilder builder = new StringBuilder();
		try {
			String url = "https://kyfw.12306.cn/otn/lcxxcx/query?purpose_codes=ADULT&queryDate=" + date + 
					"&from_station=" + from_station + "&to_station=" + to_station;
			String res = HttpBroker.getRequest(url, 3000);
			JSONObject obj = new JSONObject(res);
			JSONObject data = obj.getJSONObject("data");
			if (!data.getBoolean("flag")) {
				builder.append("没有符合条件的数据！\n");
				return builder.toString();
			}
			JSONArray datas = data.getJSONArray("datas");
			builder.append("车次\t出发站\t到达站\t出发时间\t到达时间\t历时\t商务座\t特等座\t一等座\t二等座\t高级软卧\t软卧\t硬卧\t软座\t硬座\t无座\t其它\n");
			for (int i=0; i<datas.length(); i++) {
				JSONObject msg = (JSONObject) datas.get(i);
				Data d = JaxbUtil.json2Bean(msg.toString(), Data.class);
				builder.append(d);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
	
}
