package com.ipay.res;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ipay.bean.Sub;
import com.ipay.util.ConstValue;
import com.ipay.util.RSAHelper;

/**
 * 契约查询返回结果
 * 
 * subsnum	契约数目	integer	必填	用户订购的契约数目
 * subslist	契约列表	subs_schema[]	必填	契约列表
 * subs_schema定义：
 *		参数名称	参数含义	数据类型	是否可选	参数说明
 *		waresid		商品编号	integer		必填	应用中的商品编号
 *		feetype		计费方式	integer		必填	计费方式，具体定义见附录
 *		leftcount	剩余次数	integer		可选	剩余次数，对于包次数类契约有效
 *		endtime		截止时间	String(20)	可选	契约的有效截止时间，格式为“HHHH-MM-DD HH24:MI:SS”
 *		subsstatus	契约状态	integer		必填	契约状态：
 *												0 – 正常
 *												1 – 退订
 *												2 – 暂停 
 *
 * 
 * @author visen
 *
 */
public class QuerySubRes extends IpayResponseBase {
	private Integer subsNum = null;
	private Sub[]  subsArray = null;

	public QuerySubRes( String responseStr ){
		Map<String,String> dataMap = super._parseResStr( responseStr );
		String transDataStr = dataMap.get( ConstValue.TRANSDATA );
		if( transDataStr != null ){
			JSONObject json = JSONObject.fromObject( transDataStr );
			// 检查code
			if( json.containsKey( ConstValue.CODE ) )
				this.setErrorCode( json.getString( ConstValue.CODE ) );
			// 检查errmsg
			if( json.containsKey( ConstValue.ERROR_MSG ) )
				this.setErrMsg( json.getString( ConstValue.ERROR_MSG ) );
			// 契约数量
			if( json.containsKey( ConstValue.SUB_NUM ) )
				this.setSubsNum( json.getInt( ConstValue.SUB_NUM ) );
			
			// 契约列表
			if( json.containsKey( ConstValue.SUBS_LIST ) ){
				JSONArray subsList = json.getJSONArray( ConstValue.SUBS_LIST );
				subsArray = new Sub[subsList.size()];
				for( int i = 0; i < subsArray.length; i++ ){
					JSONObject item = subsList.getJSONObject( i );
					subsArray[i] = new Sub();
					subsArray[i].setWaresId( item.getInt( ConstValue.WARES_ID ) );
					subsArray[i].setSubStatus( item.getInt( ConstValue.SUBS_STATUS ) );
					if(item.containsKey(ConstValue.LEFT_COUNT))
						subsArray[i].setLeftCount( item.getInt( ConstValue.LEFT_COUNT ) );
					subsArray[i].setFeeType( item.getInt( ConstValue.FEE_TYPE ) );
					if (item.containsKey(ConstValue.END_TIME)) 
						subsArray[i].setEndTime(  item.getString( ConstValue.END_TIME ) );
				}
			}
		}
		
		if( this.getErrorCode() != null )
			throw new RuntimeException( "请求错误：" + this.getErrMsg() );
		
		// 获取sign签名和签名类型
		String sign = dataMap.get( ConstValue.SIGN );
		
		// 验证签名
		boolean checkSign = false;
		try {
			checkSign = RSAHelper.verify( transDataStr, ConstValue.PUBLIC_KEY, sign);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if( !checkSign )
			throw new RuntimeException( "验证签名失败" );
	}
	
	
	public Integer getSubsNum() {
		return subsNum;
	}

	public void setSubsNum(Integer subsNum) {
		this.subsNum = subsNum;
	}

	public Sub[] getSubsArray() {
		return subsArray;
	}


}
