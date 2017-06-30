package com.xwc1125.droidutils.mobile;

import com.xwc1125.droidutils.LogUtils;
import com.xwc1125.droidutils.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Title: RegMobile
 * </p>
 * <p>
 * Description: 电话号码去除IP号、+86;是否是手机号
 * </p>
 * <p>
 * 对电话号码进行过滤，去掉IP号("1790", "1791", "1793", "1795","1796", "1797", "1799")，+86等前缀
 * </p>
 * 
 * @author xwc1125
 * @date 2015年11月16日下午2:30:04
 * 
 */
public class MobileUtils {
	private static final String[] IPPFXS4 = { "1790", "1791", "1793", "1795",
			"1796", "1797", "1799" };
	private static final String[] IPPFXS5 = { "12583", "12593", "12589",
			"12520", "10193", "11808" };
	private static final String[] IPPFXS6 = { "118321" };

	/**
	 * 消除电话号码中 可能含有的 IP号码、+86、0086等前缀
	 * 
	 * @param telNum
	 * @return
	 */
	public static String trimTelPhone(String telNum) {
		if (telNum == null || "".equals(telNum)) {
			LogUtils.i("MobileUtils", "trimTelPhone is null", true);
			return null;
		}

		if (telNum.length() < 6) {
			return telNum;
		}

		String ippfx6 = substring(telNum, 0, 6);
		String ippfx5 = substring(telNum, 0, 5);
		String ippfx4 = substring(telNum, 0, 4);

		if (telNum.length() > 7
				&& (substring(telNum, 5, 1).equals("0")
						|| substring(telNum, 5, 1).equals("1")
						|| substring(telNum, 5, 3).equals("400") || substring(
							telNum, 5, 3).equals("+86"))
				&& (inArray(ippfx5, IPPFXS5) || inArray(ippfx4, IPPFXS4)))
			telNum = substring(telNum, 5);
		else if (telNum.length() > 8
				&& (substring(telNum, 6, 1).equals("0")
						|| substring(telNum, 6, 1).equals("1")
						|| substring(telNum, 6, 3).equals("400") || substring(
							telNum, 6, 3).equals("+86"))
				&& inArray(ippfx6, IPPFXS6))
			telNum = substring(telNum, 6);
		// remove ip dial

		telNum = telNum.replace("-", "");
		telNum = telNum.replace(" ", "");

		if (substring(telNum, 0, 4).equals("0086"))
			telNum = substring(telNum, 4);
		else if (substring(telNum, 0, 3).equals("+86"))
			telNum = substring(telNum, 3);
		else if (substring(telNum, 0, 2).equals("86"))
			telNum = substring(telNum, 2);
		else if (substring(telNum, 0, 5).equals("00186"))
			telNum = substring(telNum, 5);
		return telNum;
	}

	/**
	 * 截取字符串
	 * 
	 * @param s
	 * @param from
	 * @return
	 */
	protected static String substring(String s, int from) {
		try {
			return s.substring(from);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	protected static String substring(String s, int from, int len) {
		try {
			return s.substring(from, from + len);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 判断一个字符串是否在一个字符串数组中
	 * 
	 * @param target
	 * @param arr
	 * @return
	 */
	protected static boolean inArray(String target, String[] arr) {
		if (arr == null || arr.length == 0) {
			return false;
		}
		if (target == null) {
			return false;
		}
		for (String s : arr) {
			if (target.equals(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * Title: isMobileNo
	 * </p>
	 * <p>
	 * Description: 判断是否为手机号
	 * </p>
	 * <p>
	 * * 电信：133 1349 153 180 181 189 1700 177<br>
	 * 移动：1340-1348 135 136 137 138 139 150 151 152 157 158 159 182 183 184 187
	 * 188 178 147 1705<br>
	 * 联通：130 131 132 155 156 185 186 176 145 1709<br>
	 * </p>
	 * 
	 * @param mobiles
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015-7-13下午1:52:47
	 */
	public static boolean isMobileNo(String mobiles) {
		if (StringUtils.isEmpty(mobiles)) {
			return false;
		}
		mobiles = MobileUtils.trimTelPhone(mobiles);
		// 原始："^((13[0-9])|(15[^4,\\D])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$"
		Pattern p = Pattern
				.compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 判断号码是联通，移动，电信中的哪个 在使用本方法前，请先验证号码的合法性 规则： 前三位为130-133 联通
	 * 前三位为135-139或前四位为1340-1348 移动； 其它的应该为电信
	 * 
	 * @param mobile 要判断的号码
	 * @return 返回相应类型：1代表联通；2代表移动；3代表电信
	 */
	public static int getMobileType2(String mobile) {
		if (mobile.startsWith("0") || mobile.startsWith("+860")) {
			mobile = mobile.substring(mobile.indexOf("0") + 1, mobile.length());
		}
		List<?> chinaUnicom1 = Arrays.asList(new String[] { "130", "131",
				"132", "153", "155", "156", "185", "186" });
		List<?> chinaUnicom2 = Arrays.asList(new String[] { "1709" });
		List<?> chinaMobile1 = Arrays.asList(new String[] { "135", "136",
				"137", "138", "139", "147", "150", "151", "152", "157", "158",
				"159", "182", "187", "188" });
		List<?> chinaMobile2 = Arrays
				.asList(new String[] { "1340", "1341", "1342", "1343", "1344",
						"1345", "1346", "1347", "1348", "1705" });
		// List<?> chinaNet = Arrays.asList(new String[] { "1700" });
		boolean bolChinaUnicom1 = (chinaUnicom1
				.contains(mobile.substring(0, 3)));
		boolean bolChinaUnicom2 = (chinaUnicom2
				.contains(mobile.substring(0, 3)));
		boolean bolChinaMobile1 = (chinaMobile1
				.contains(mobile.substring(0, 3)));
		boolean bolChinaMobile2 = (chinaMobile2
				.contains(mobile.substring(0, 4)));
		if (bolChinaUnicom1 || bolChinaUnicom2)
			return 1;// 联通
		if (bolChinaMobile1 || bolChinaMobile2)
			return 2; // 移动
		return 3; // 其他为电信
	}

	/**
	 * 
	 * @Title getMobileType
	 * @Description 获取手机号的类型
	 * @param @param mobile
	 * 
	 *        电信：133 1349 153 180 181 189 1700 177<br>
	 *        移动：1340-1348 135 136 137 138 139 150 151 152 157 158 159 182 183
	 *        184 187 188 178 147 1705<br>
	 *        联通：130 131 132 155 156 185 186 176 145 1709<br>
	 * 
	 * @param @return
	 * @return int
	 * @author xwc1125
	 * @date 2016年1月8日 下午12:49:05
	 */
	public static int getMobileType(String mobile) {
		mobile = trimTelPhone(mobile);
		// 联通
		List<?> chinaUnicom1 = Arrays.asList(new String[] { "130", "131",
				"132", "155", "156", "176", "185", "186", "145" });
		List<?> chinaUnicom2 = Arrays.asList(new String[] { "1709" });
		// 移动
		List<?> chinaMobile1 = Arrays.asList(new String[] { "135", "136",
				"137", "138", "139", "150", "151", "152", "157", "158", "159",
				"178", "182", "183", "184", "187", "188", "147" });
		List<?> chinaMobile2 = Arrays
				.asList(new String[] { "1340", "1341", "1342", "1343", "1344",
						"1345", "1346", "1347", "1348", "1705" });
		// 电信
		// List<?> chinaNet1 = Arrays.asList(new String[] { "133", "153", "177",
		// "180", "181", "189" });
		// List<?> chinaNet2 = Arrays.asList(new String[] { "1349", "1700" });

		boolean bolChinaUnicom1 = (chinaUnicom1
				.contains(mobile.substring(0, 3)));
		boolean bolChinaUnicom2 = (chinaUnicom2
				.contains(mobile.substring(0, 3)));
		boolean bolChinaMobile1 = (chinaMobile1
				.contains(mobile.substring(0, 3)));
		boolean bolChinaMobile2 = (chinaMobile2
				.contains(mobile.substring(0, 4)));
		if (bolChinaUnicom1 || bolChinaUnicom2)
			return 1;// 联通
		if (bolChinaMobile1 || bolChinaMobile2)
			return 2; // 移动
		return 3; // 其他为电信
	}

	/**
	 * 
	 * @Title getShowNumber
	 * @Description 获取半显示手机号
	 * @param @param mobile【只有前三后四被显示，中间用*代替】
	 * @param @return
	 * @return String
	 * @author xwc1125
	 * @date 2016年3月6日 上午9:19:35
	 */
	public static String getShowNumber(String mobile) {
		String result = null;
		if (StringUtils.isEmpty(mobile)) {
			return result;
		}
		int length = mobile.length();
		if (length < 8) {
			return mobile;
		}
		String pre_mobile = mobile.substring(0, 3);
		String post_mobile = mobile.substring(length - 4);
		String mid_mobile = "";
		for (int i = 0; i < length - 7; i++) {
			mid_mobile += "*";
		}
		return pre_mobile + mid_mobile + post_mobile;
	}

	/**
	 * 
	 * @Title getTelOprTypeByImsi
	 * @Description 通过解析Imsi获取当前的手机号是什么运营商
	 * @param @param is IMSI=MCC+MNC+MSIN; MCC（Mobile Country
	 *        Code，移动国家码）：MCC的资源由国际电信联盟（ITU）在全世界范围内统一分配和管理
	 *        ，唯一识别移动用户所属的国家，共3位，中国为460。<br>
	 *        MNC（Mobile Network Code，移动网络号码）：用于识别移动用户所归属的移动通信网，2~3位。<br>
	 *        在同一个国家内，如果有多个PLMN（Public Land Mobile Network，公共陆地移动网
	 *        ，一般某个国家的一个运营商对应一个PLMN），可以通过MNC来进行区别 ，即每一个PLMN都要分配唯一的MNC
	 *        。中国移动系统使用00、02、04、07，中国联通GSM系统使用01
	 *        、06，中国电信CDMA系统使用03、05、电信4G使用11，中国铁通系统使用20。<br>
	 *        MSIN（Mobile Subscriber Identification
	 *        Number，移动用户识别号码）：用以识别某一移动通信网中的移动用户。共有10位，其结构如下： EF+M0M1M2M3+ABCD<br>
	 *        其中，EF由运营商分配；M0M1M2M3和MDN（Mobile Directory
	 *        Number，移动用户号码簿号码）中的H0H1H2H3可存在对应关系；ABCD：四位，自由分配。<br>
	 * 
	 * @param @return
	 * @return TELOPR_TYPE
	 * @author xwc1125
	 * @date 2016年3月16日 下午4:28:50
	 */
	public static TelecomOperatorsConfig.TELOPR_TYPE getTelOprTypeByImsi(String is) {
		TelecomOperatorsConfig.TELOPR_TYPE telOprType = TelecomOperatorsConfig.TELOPR_TYPE.UNKNOW;
		if (StringUtils.isNotEmpty(is)) {
			// 如果全部是0，那么将不知道运营商的信息
			String imsi_1 = is.replaceAll("0", "");
			if (StringUtils.isEmpty(imsi_1)) {
				return telOprType;
			}
			// 获取Imsi中的mnc的两位
			String mnc = is.substring(3, 5);
			if (mnc.equals("00") || mnc.equals("02") || mnc.equals("04")
					|| mnc.equals("07")) {
				telOprType = TelecomOperatorsConfig.TELOPR_TYPE.CMCC;
			} else if (mnc.equals("01") || mnc.equals("06")) {
				telOprType = TelecomOperatorsConfig.TELOPR_TYPE.CUCC;
			} else if (mnc.equals("03") || mnc.equals("05") || mnc.equals("11")) {
				telOprType = TelecomOperatorsConfig.TELOPR_TYPE.CTC;
			}
		}
		return telOprType;
	}
}
