package com.njq.start.testcontroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

@RestController
public class TestController {

	@RequestMapping("f/test/tt/f")
	public String aaa() {
		return "你你你";
	}
	
	public void test() {
		String a1 = new String("ggg");
		List<String> l1 = new ArrayList<>();
		l1.add("abc");
		List<String> l2 = null;
		this.test1(l1, l2,a1);
		System.out.println(l1);
		System.out.println(l2);
		System.out.println(a1);
	}
	
	public void test1(List<String> l1,List<String> l2,String abc) {
		l1=new ArrayList<>();
		l1.add("123");
		l2=new ArrayList<>();
		l2.add("234");
		abc = new String("gggg");
		
		
		List<String> l3= new ArrayList<>();
		l1=l3;
	}
	
	public static void main(String[] args) {
//		TestController tt = new TestController();
//		tt.test();
//		List<String> ll =new ArrayList<>();
//		ll.addAll(null);
//		System.out.println(ll);

		List<String> cityidsList = Arrays.asList("4,2,".split(","));
		Long cityId = 5L;
		System.out.println(cityidsList.contains(cityId.toString()));
		System.out.println(cityId.toString().equals("4"));
//		String a = "tttff9";
//		if(a.substring(5, 6).matches("\\d+")){
//			a = a.substring(0, 5);
//		}
//		if(a.substring(0, 1).matches("\\d+")){
//			a = a.substring(1, 6);
//		}
//		System.out.println( a);
//		for(int i = 0 ;i <10;i++){
//			System.out.println(a+i);
//		}
//		for(int i = 0 ;i <10;i++){
//			System.out.println(i+a);
//		}

//		List<String> ll =new ArrayList<>();
//		ll.add("1111");
//		ll.add("222");
//		ll.add("333");
//		System.out.println(JSON.toJSONString(ll));
		
//		System.out.println(ll.get(0));
		
		
		
//		List<RedPackageDefaultImageInfo> list=new ArrayList<>();
//		RedPackageDefaultImageInfo info1=new RedPackageDefaultImageInfo();
//		info1.setReceiveDialogList(new ArrayList<>());
//		info1.setReceiveIconList(new ArrayList<>());
//		info1.setShareBuoyList(new ArrayList<>());
//		info1.setShareDialogList(new ArrayList<>());
//		info1.setTopPicList(new ArrayList<>());
//		info1.setYhSellerList(new ArrayList<>());
//		info1.setFirstMarkingImgList(new ArrayList<>());
//		RedPackagePictureInfo i7=new RedPackagePictureInfo();
//		i7.setRelevanceLabel("red");
//		i7.setPicSrc("http://pic15.nipic.com/20110713/4224370_055401259129_2.jpg");
//		info1.getTopPicList().add(i7);
//		RedPackagePictureInfo ia=new RedPackagePictureInfo();
//		ia.setRelevanceLabel("green");
//		ia.setPicSrc("http://pic19.nipic.com/20120308/4970979_102637717125_2.jpg");
//		info1.getTopPicList().add(ia);
//		
//		
//		RedPackagePictureInfo i1=new RedPackagePictureInfo();
//		i1.setRelevanceLabel("red");
//		i1.setPicSrc("http://pic1.nipic.com/2008-11-12/20081112215448893_2.jpg");
//		info1.getReceiveIconList().add(i1);
//		RedPackagePictureInfo i2=new RedPackagePictureInfo();
//		i2.setRelevanceLabel("red");
//		i2.setPicSrc("http://pic15.nipic.com/20110713/4224370_055401259129_2.jpg");
//		info1.getReceiveIconList().add(i2);
//		
//		RedPackagePictureInfo ib=new RedPackagePictureInfo();
//		ib.setRelevanceLabel("green");
//		ib.setPicSrc("http://pic15.nipic.com/20110713/4224370_055401259129_2.jpg");
//		info1.getReceiveIconList().add(ib);
//		RedPackagePictureInfo ic=new RedPackagePictureInfo();
//		ic.setRelevanceLabel("green");
//		ic.setPicSrc("http://pic1a.nipic.com/2009-01-07/20091713417344_2.jpg");
//		info1.getReceiveIconList().add(ic);
//		
//		
//		
//		RedPackagePictureInfo i3=new RedPackagePictureInfo();
//		i3.setRelevanceLabel("red");
//		i3.setPicSrc("http://pic15.nipic.com/20110713/4224370_055401259129_2.jpg");
//		info1.getReceiveDialogList().add(i3);
//		RedPackagePictureInfo i4=new RedPackagePictureInfo();
//		i4.setRelevanceLabel("red");
//		i4.setPicSrc("http://pic15.nipic.com/20110713/4224370_055401259129_2.jpg");
//		info1.getShareBuoyList().add(i4);
//		RedPackagePictureInfo i5=new RedPackagePictureInfo();
//		i5.setRelevanceLabel("red");
//		i5.setPicSrc("http://pic15.nipic.com/20110713/4224370_055401259129_2.jpg");
//		info1.getShareDialogList().add(i5);
//		RedPackagePictureInfo i6=new RedPackagePictureInfo();
//		i6.setRelevanceLabel("red");
//		i6.setPicSrc("http://pic5.nipic.com/20100105/2685384_090503693048_2.jpg");
//		info1.getShareDialogList().add(i6);
//		RedPackagePictureInfo i8=new RedPackagePictureInfo();
//		i8.setRelevanceLabel("red");
//		i8.setPicSrc("http://pic15.nipic.com/20110713/4224370_055401259129_2.jpg");
//		info1.getFirstMarkingImgList().add(i8);
//		info1.getYhSellerList().add(YHSeller.HYD);
//		info1.getYhSellerList().add(YHSeller.MALL);
//		info1.getYhSellerList().add(YHSeller.BRAVO);
//		info1.getYhSellerList().add(YHSeller.CSX);
//		info1.getYhSellerList().add(YHSeller.BRAVO_JB);
//		info1.getYhSellerList().add(YHSeller.SUPER_SPECIES);
//		info1.getYhSellerList().add(YHSeller.SUPER_STORES);
//		info1.getYhSellerList().add(YHSeller.CSX_MALL);
//		info1.getYhSellerList().add(YHSeller.EXCELLENT_STORES);
//		info1.getYhSellerList().add(YHSeller.SHANG_GREENS);
//		info1.getYhSellerList().add(YHSeller.JISUDA);
//		info1.getYhSellerList().add(YHSeller.ZHONGBAI);
//		info1.getYhSellerList().add(YHSeller.YONGHUIGUANJIA);
//		list.add(info1);
		
//		RedPackageDefaultImageInfo info2=new RedPackageDefaultImageInfo();
//		info2.setReceiveDialogList(new ArrayList<>());
//		info2.setReceiveIconList(new ArrayList<>());
//		info2.setShareBuoyList(new ArrayList<>());
//		info2.setShareDialogList(new ArrayList<>());
//		info2.setTopPicList(new ArrayList<>());
//		info2.setYhSellerList(new ArrayList<>());
//		info2.setFirstMarkingImgList(new ArrayList<>());
//		info2.getReceiveDialogList().add("http://pic14.nipic.com/20110428/2945454_142816670156_2.jpg");
//		info2.getReceiveIconList().add("http://pic1.nipic.com/2008-11-12/20081112215448893_2.jpg");
//		info2.getReceiveIconList().add("http://pic14.nipic.com/20110428/2945454_142816670156_2.jpg");
//		info2.getShareBuoyList().add("http://pic14.nipic.com/20110428/2945454_142816670156_2.jpg");
//		info2.getShareDialogList().add("http://pic5.nipic.com/20100105/2685384_090503693048_2.jpg");
//		info2.getShareDialogList().add("http://pic14.nipic.com/20110428/2945454_142816670156_2.jpg");
//		info2.getTopPicList().add("http://pic14.nipic.com/20110428/2945454_142816670156_2.jpg");
//		info2.getFirstMarkingImgList().add("http://pic14.nipic.com/20110428/2945454_142816670156_2.jpg");
//		info2.getYhSellerList().add(YHSeller.BRAVO);
//		info2.getYhSellerList().add(YHSeller.EXCELLENT_STORES);
//		info2.getYhSellerList().add(YHSeller.BRAVO_JB);
//		list.add(info2);
//		
//		
//		RedPackageDefaultImageInfo info3=new RedPackageDefaultImageInfo();
//		info3.setReceiveDialogList(new ArrayList<>());
//		info3.setReceiveIconList(new ArrayList<>());
//		info3.setShareBuoyList(new ArrayList<>());
//		info3.setShareDialogList(new ArrayList<>());
//		info3.setTopPicList(new ArrayList<>());
//		info3.setYhSellerList(new ArrayList<>());
//		info3.setFirstMarkingImgList(new ArrayList<>());
//		info3.getReceiveDialogList().add("http://pic19.nipic.com/20120309/9448607_174010404171_2.jpg");
//		info3.getReceiveIconList().add("http://pic19.nipic.com/20120309/9448607_174010404171_2.jpg");
//		info3.getReceiveIconList().add("http://pic1.nipic.com/2008-11-12/20081112215448893_2.jpg");
//		info3.getShareBuoyList().add("http://pic19.nipic.com/20120309/9448607_174010404171_2.jpg");
//		info3.getShareDialogList().add("http://pic19.nipic.com/20120309/9448607_174010404171_2.jpg");
//		info3.getShareDialogList().add("http://pic5.nipic.com/20100105/2685384_090503693048_2.jpg");
//		info3.getTopPicList().add("http://pic19.nipic.com/20120309/9448607_174010404171_2.jpg");
//		info3.getFirstMarkingImgList().add("http://pic19.nipic.com/20120309/9448607_174010404171_2.jpg");
//		info3.getYhSellerList().add(YHSeller.YONGHUIGUANJIA);
//		list.add(info3);
//		
//		
//		RedPackageDefaultImageInfo info4=new RedPackageDefaultImageInfo();
//		info4.setReceiveDialogList(new ArrayList<>());
//		info4.setReceiveIconList(new ArrayList<>());
//		info4.setShareBuoyList(new ArrayList<>());
//		info4.setShareDialogList(new ArrayList<>());
//		info4.setTopPicList(new ArrayList<>());
//		info4.setYhSellerList(new ArrayList<>());
//		info4.setFirstMarkingImgList(new ArrayList<>());
//		info4.getReceiveDialogList().add("http://pic15.nipic.com/20110606/7655334_130023193128_2.jpg");
//		info4.getReceiveIconList().add("http://pic15.nipic.com/20110606/7655334_130023193128_2.jpg");
//		info4.getReceiveIconList().add("http://pic1.nipic.com/2008-11-12/20081112215448893_2.jpg");
//		info4.getReceiveIconList().add("http://pic9.nipic.com/20100902/2634566_094059088112_2.jpg");
//		info4.getReceiveIconList().add("http://pic3.nipic.com/20090610/1158330_021338972_2.jpg");
//		info4.getShareBuoyList().add("http://pic15.nipic.com/20110606/7655334_130023193128_2.jpg");
//		info4.getShareDialogList().add("http://pic15.nipic.com/20110606/7655334_130023193128_2.jpg");
//		info4.getShareDialogList().add("http://pic5.nipic.com/20100105/2685384_090503693048_2.jpg");
//		info4.getTopPicList().add("http://pic15.nipic.com/20110606/7655334_130023193128_2.jpg");
//		info4.getTopPicList().add("http://pic5.nipic.com/20100105/2685384_090503693048_2.jpg");
//		info4.getFirstMarkingImgList().add("http://pic15.nipic.com/20110606/7655334_130023193128_2.jpg");
//		info4.getYhSellerList().add(YHSeller.SUPER_SPECIES);
//		list.add(info4);
//		
//		
//		RedPackageDefaultImageInfo info5=new RedPackageDefaultImageInfo();
//		info5.setReceiveDialogList(new ArrayList<>());
//		info5.setReceiveIconList(new ArrayList<>());
//		info5.setShareBuoyList(new ArrayList<>());
//		info5.setShareDialogList(new ArrayList<>());
//		info5.setTopPicList(new ArrayList<>());
//		info5.setYhSellerList(new ArrayList<>());
//		info5.setFirstMarkingImgList(new ArrayList<>());
//		info5.getReceiveDialogList().add("http://pic4.nipic.com/20091130/3500973_180048071609_2.jpg");
//		info5.getReceiveIconList().add("http://pic4.nipic.com/20091130/3500973_180048071609_2.jpg");
//		info5.getShareBuoyList().add("http://pic4.nipic.com/20091130/3500973_180048071609_2.jpg");
//		info5.getShareDialogList().add("http://pic4.nipic.com/20091130/3500973_180048071609_2.jpg");
//		info5.getShareDialogList().add("http://pic3.nipic.com/20090610/1158330_021338972_2.jpg");
//		info5.getTopPicList().add("http://pic4.nipic.com/20091130/3500973_180048071609_2.jpg");
//		info5.getFirstMarkingImgList().add("http://pic4.nipic.com/20091130/3500973_180048071609_2.jpg");
//		info5.getYhSellerList().add(YHSeller.CSX);
//		info5.getYhSellerList().add(YHSeller.CSX_MALL);
//		info5.getYhSellerList().add(YHSeller.SHANG_GREENS);
//		list.add(info5);
		
		
		
		
		
		
//		String text = JSON.toJSONString(list);
//		System.out.println(text);
//		
//		
//		List<RedPackageDefaultImageInfo> xxx =JSON.parseArray(text, RedPackageDefaultImageInfo.class);
//		System.out.println(xxx.get(0).getYhSellerList().get(0).getValue());
	}
}
