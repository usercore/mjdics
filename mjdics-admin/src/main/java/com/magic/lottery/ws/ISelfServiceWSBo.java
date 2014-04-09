package com.magic.lottery.ws;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * 所有方法的调用结果以XML格式返回
 * 
 * @see WebService接口文档.doc
 * 
 * 
 * @author Administrator
 * 
 */
@WebService
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE, use = SOAPBinding.Use.ENCODED, style = SOAPBinding.Style.RPC)
public interface ISelfServiceWSBo {

	/**
	 * 1.获取通讯密钥
	 * 
	 * @param id
	 *            渠道ID
	 * @param sign
	 *            签名（使用签名密钥对渠道ID进行AES加密）
	 * @return
	 */
	public String getSecuretKeyByID(@WebParam(name = "id") String id, @WebParam(name = "sign") String sign);

	/**
	 * 转换通讯密钥
	 * 
	 * @param key
	 * @return
	 */
	public String transSignKey(@WebParam(name = "id") String id, @WebParam(name = "key") String key);

	/**
	 * 2.获取零钱彩票
	 * 
	 * @param id
	 *            渠道ID
	 * @param userID
	 *            被赠用户的ID标识
	 * @param amount
	 *            赠送金额（单位：分）
	 * @param num
	 *            赠送数量
	 * @param orderID
	 *            订单号
	 * @param sign
	 *            签名（使用通讯密钥对userID+id+orderID+amount+num组成的字串进行AES加密）
	 * @return
	 */
	public String getLottery(@WebParam(name = "id") String id, @WebParam(name = "userID") String userID,
			@WebParam(name = "amount") int amount, @WebParam(name = "num") int num,
			@WebParam(name = "orderID") String orderID, @WebParam(name = "sign") String sign);

	/**
	 * 3.设置或修改用户手机号码
	 * 
	 * @param id
	 *            渠道ID
	 * @param userID
	 *            被赠用户的ID标识
	 * @param mobile
	 *            用户新手机号码
	 * @param sign
	 *            签名 （使用通讯密钥对userID+id+mobile组成的字串进行AES加密）
	 * @return
	 */
	public String updateMobile(@WebParam(name = "id") String id, @WebParam(name = "userID") String userID,
			@WebParam(name = "mobile") String mobile, @WebParam(name = "sign") String sign);

	/**
	 * 4.更新用户身份证号
	 * 
	 * @param id
	 *            渠道ID
	 * @param userID
	 *            被赠用户ID标识
	 * @param cardID
	 *            用户身份证号
	 * @param sign
	 *            签名（使用通讯密钥对userID+id+cardID组成的字串进行AES加密）
	 * @return
	 */
	public String updateUserCardID(@WebParam(name = "id") String id, @WebParam(name = "userID") String userID,
			@WebParam(name = "cardID") String cardID, @WebParam(name = "sign") String sign);

	/**
	 * 5.销售对账
	 * 
	 * @param id
	 *            渠道ID
	 * @param fromSeqNo
	 *            开始循序号
	 * @param toSeqNo
	 *            结束顺序号
	 * @param amount
	 *            销售总额
	 * @param sign
	 *            签名（使用通讯密钥对amount+id+fromSeqNo+toSeqNo组成的字串进行AES加密）
	 * @return
	 */
	public String sellChecking(@WebParam(name = "id") String id, @WebParam(name = "fromSeqNo") int fromSeqNo,
			@WebParam(name = "toSeqNo") int toSeqNo, @WebParam(name = "amount") int amount,
			@WebParam(name = "sign") String sign);

	/**
	 * 6.获取中奖记录
	 * 
	 * @param id
	 *            渠道ID
	 * @param issue
	 *            期号，为""时查询所有期，不能为空对象
	 * @param sign
	 *            签名 （使用通讯密钥对issue + id组成的字串进行AES加密）
	 * @return
	 */
	public String getAwardInfo(@WebParam(name = "id") String id, @WebParam(name = "issue") String issue,
			@WebParam(name = "sign") String sign);

	/**
	 * 7.查询用户赠彩记录
	 * 
	 * @param id
	 *            渠道ID
	 * @param userID
	 *            被赠用户的ID标识
	 * @param type
	 *            彩票状态类型，（-1：所有，0：等待开奖，1：未中奖，2：已中奖）
	 * @param page
	 *            页码，从1开始
	 * @param pageSize
	 *            每页条目数
	 * @param sign
	 *            签名 （使用通讯密钥对userID + id + type + page + pageSize组成的字串进行AES加密）
	 * @return
	 */
	public String getLotteryListByUserID(@WebParam(name = "id") String id, @WebParam(name = "userID") String userID,
			@WebParam(name = "type") int type, @WebParam(name = "page") int page,
			@WebParam(name = "pageSize") int pageSize, @WebParam(name = "sign") String sign);

	/**
	 * 8.查询开奖号码
	 * 
	 * @param id
	 *            渠道ID
	 * @param issue
	 *            期号，为""时查询所有期，不能为null
	 * @param page
	 *            页码，从1开始
	 * @param pageSize
	 *            每页条目数
	 * @param sign
	 *            签名 （使用通讯密钥对issue + id + page + pageSize组成的字串进行AES加密）
	 * @return
	 */
	public String getLotteryResult(@WebParam(name = "id") String id, @WebParam(name = "issue") String issue,
			@WebParam(name = "page") int page, @WebParam(name = "pageSize") int pageSize,
			@WebParam(name = "sign") String sign);

	/**
	 * 9.获取用户彩金账户信息
	 * 
	 * @param id
	 *            渠道ID
	 * @param userID
	 *            被赠用户的ID标识
	 * @param sign
	 *            签名（使用通讯密钥对userID+id组成的字串进行AES加密）
	 * @return
	 */
	public String getAccountInfo(@WebParam(name = "id") String id, @WebParam(name = "userID") String userID,
			@WebParam(name = "sign") String sign);

	/**
	 * 10.用户彩金账户奖金兑现
	 * 
	 * @param id
	 *            渠道ID
	 * @param userID
	 *            被赠用户的ID标识
	 * @param orderID
	 *            订单号
	 * @param amount
	 *            兑现金额（单位：分）
	 * @param sign
	 *            签名（使用通讯密钥对userID+id+orderID+amount组成的字串进行AES加密）
	 * @return
	 */
	public String encash(@WebParam(name = "id") String id, @WebParam(name = "userID") String userID,
			@WebParam(name = "orderID") String orderID, @WebParam(name = "amount") int amount,
			@WebParam(name = "sign") String sign);

	/**
	 * 11.参数设定
	 * 
	 * @param id
	 *            渠道ID
	 * @param dayLimit
	 *            日赠送限制额度
	 * @param sign
	 *            （使用签名密钥对dayLimit+id进行AES加密）
	 * @return
	 */
	public String setParams(@WebParam(name = "id") String id, @WebParam(name = "dayLimit") int dayLimit,
			@WebParam(name = "sign") String sign);

	/**
	 * 12.获取参数
	 * 
	 * @param id
	 *            渠道ID
	 * @param sign
	 *            （使用签名密钥对id进行AES加密）
	 * @return
	 */
	public String getParams(@WebParam(name = "id") String id, @WebParam(name = "sign") String sign);

	/**
	 * 查询当前销售期
	 * 
	 * @param id
	 *            渠道ID
	 * @param gameCode
	 *            游戏代码
	 * @param sign
	 *            （使用签名密钥对id+gameCode进行AES加密）
	 * @return
	 */
	public String getCurrentIssue(@WebParam(name = "id") String id, @WebParam(name = "gameCode") int gameCode,
			@WebParam(name = "sign") String sign);

	/**
	 * 查询期状态
	 * 
	 * @param id
	 *            渠道ID
	 * @param gameCode
	 *            游戏代码
	 * @param issue
	 *            期号
	 * @param sign
	 *            （使用签名密钥对id+gameCode+issue进行AES加密）
	 * @return
	 */
	public String getIssueStatus(@WebParam(name = "id") String id, @WebParam(name = "gameCode") int gameCode,
			@WebParam(name = "issue") String issue, @WebParam(name = "sign") String sign);

	/**
	 * 获取零钱彩票(自选号码)
	 * 
	 * @param id
	 *            渠道ID
	 * @param userID
	 *            被赠用户的ID标识
	 * @param lotteryData
	 *            用户自选彩票号码
	 * @param gameCode
	 *            游戏代码（双色球为201）
	 * @param amount
	 *            赠送金额（单位：分）
	 * @param orderID
	 *            订单号
	 * @param sign
	 *            签名（使用通讯密钥对userID+id+lotteryData+gameCode+orderID+
	 *            amount组成的字串进行AES加密）
	 * @return
	 */
	public String getLotteryCustom(@WebParam(name = "id") String id, @WebParam(name = "userID") String userID,
			@WebParam(name = "lotteryData") String lotteryData, @WebParam(name = "gameCode") int gameCode,
			@WebParam(name = "amount") int amount, @WebParam(name = "orderID") String orderID,
			@WebParam(name = "sign") String sign);
	/**
	 * 13.赠送零钱彩票
	 * 
	 * @param id
	 *            渠道ID
	 * @param mobile
	 *            用户手机号码
	 * @param amount
	 *            赠送金额（单位：分,最大200，不超过一注彩票）
	 * @param num
	 *            赠送数量
	 * @param orderID
	 *            订单号
	 * @param sign
	 *            签名（使用通讯密钥对mobile+id+orderID+amount+num组成的字串进行AES加密）
	 * @return
	 * 
	 *         public String tpPresentLottery(@WebParam(name = "id") String id,
	 * @WebParam(name = "mobile") String mobile,
	 * @WebParam(name = "amount") int amount, @WebParam(name = "num") int num,
	 * @WebParam(name = "orderID") String orderID, @WebParam(name = "sign")
	 *                String sign);
	 */
}
