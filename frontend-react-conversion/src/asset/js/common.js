/* HTML5 Placeholder jQuery Plugin - v2.3.1
 * Copyright (c)2015 Mathias Bynens
 * 2015-12-16
 * https://github.com/mathiasbynens/jquery-placeholder
 */
!function(a){"function"==typeof define&&define.amd?define(["jquery"],a):a("object"==typeof module&&module.exports?require("jquery"):jQuery)}(function(a){function b(b){var c={},d=/^jQuery\d+$/;return a.each(b.attributes,function(a,b){b.specified&&!d.test(b.name)&&(c[b.name]=b.value)}),c}function c(b,c){var d=this,f=a(this);if(d.value===f.attr(h?"placeholder-x":"placeholder")&&f.hasClass(n.customClass))if(d.value="",f.removeClass(n.customClass),f.data("placeholder-password")){if(f=f.hide().nextAll('input[type="password"]:first').show().attr("id",f.removeAttr("id").data("placeholder-id")),b===!0)return f[0].value=c,c;f.focus()}else d==e()&&d.select()}function d(d){var e,f=this,g=a(this),i=f.id;if(!d||"blur"!==d.type||!g.hasClass(n.customClass))if(""===f.value){if("password"===f.type){if(!g.data("placeholder-textinput")){try{e=g.clone().prop({type:"text"})}catch(j){e=a("<input>").attr(a.extend(b(this),{type:"text"}))}e.removeAttr("name").data({"placeholder-enabled":!0,"placeholder-password":g,"placeholder-id":i}).bind("focus.placeholder",c),g.data({"placeholder-textinput":e,"placeholder-id":i}).before(e)}f.value="",g=g.removeAttr("id").hide().prevAll('input[type="text"]:first').attr("id",g.data("placeholder-id")).show()}else{var k=g.data("placeholder-password");k&&(k[0].value="",g.attr("id",g.data("placeholder-id")).show().nextAll('input[type="password"]:last').hide().removeAttr("id"))}g.addClass(n.customClass),g[0].value=g.attr(h?"placeholder-x":"placeholder")}else g.removeClass(n.customClass)}function e(){try{return document.activeElement}catch(a){}}var f,g,h=!1,i="[object OperaMini]"===Object.prototype.toString.call(window.operamini),j="placeholder"in document.createElement("input")&&!i&&!h,k="placeholder"in document.createElement("textarea")&&!i&&!h,l=a.valHooks,m=a.propHooks,n={};j&&k?(g=a.fn.placeholder=function(){return this},g.input=!0,g.textarea=!0):(g=a.fn.placeholder=function(b){var e={customClass:"placeholder"};return n=a.extend({},e,b),this.filter((j?"textarea":":input")+"["+(h?"placeholder-x":"placeholder")+"]").not("."+n.customClass).not(":radio, :checkbox, [type=hidden]").bind({"focus.placeholder":c,"blur.placeholder":d}).data("placeholder-enabled",!0).trigger("blur.placeholder")},g.input=j,g.textarea=k,f={get:function(b){var c=a(b),d=c.data("placeholder-password");return d?d[0].value:c.data("placeholder-enabled")&&c.hasClass(n.customClass)?"":b.value},set:function(b,f){var g,h,i=a(b);return""!==f&&(g=i.data("placeholder-textinput"),h=i.data("placeholder-password"),g?(c.call(g[0],!0,f)||(b.value=f),g[0].value=f):h&&(c.call(b,!0,f)||(h[0].value=f),b.value=f)),i.data("placeholder-enabled")?(""===f?(b.value=f,b!=e()&&d.call(b)):(i.hasClass(n.customClass)&&c.call(b),b.value=f),i):(b.value=f,i)}},j||(l.input=f,m.value=f),k||(l.textarea=f,m.value=f),a(function(){a(document).delegate("form","submit.placeholder",function(){var b=a("."+n.customClass,this).each(function(){c.call(this,!0,"")});setTimeout(function(){b.each(d)},10)})}),a(window).bind("beforeunload.placeholder",function(){var b=!0;try{"javascript:void(0)"===document.activeElement.toString()&&(b=!1)}catch(c){}b&&a("."+n.customClass).each(function(){this.value=""})}))});

/*
 * 설명   : jQuery 메서드 모음
 * 사용처 : document.ready 구문에 실행함수 탑재
 */
(function ($, window, document, undefined) {
	$.fn.closest_descendent = function(filter) {
		var $found = $(),
			$currentSet = this; // Current place
		while ($currentSet.length) {
			$found = $currentSet.filter(filter);
			if ($found.length) break;  // At least one match: break loop
			// Get all children of the current set
			$currentSet = $currentSet.children();
		}
		return $found.first(); // Return first match of the collection
	} 
	$.fn.setTabMenu = function(){
		return this.each(function(){
			$('.ui-tab-menu a').on('click', this, function(e){
				var wrapTab = $(this).closest('.o-tab-menu');
				var tabMenu = $(this).closest('.ui-tab-menu');
				if(!$(this).closest('li').hasClass('on')){
					var show = $(this).attr('href');
					wrapTab.closest_descendent('.tab-on').removeClass('tab-on');
					wrapTab.find(show).addClass('tab-on');
					tabMenu.find('.on').removeClass('on');
					$(this).closest('li').addClass('on');
				}
				e.preventDefault();
			});
		});
	}
}(window.jQuery, window, document));

/*
 * 설명   : comUtil 메서드 모음
 */
var comUtil = window.comUtil || (function(){
	return {
		setCookie: function(name, value, expiredays){
			var today = new Date();
			today.setDate( today.getDate() + expiredays );
			document.cookie = name + "=" + escape( value ) + "; expires=" + today.toGMTString() + "; path=/";
		},
		getCookie: function(name){
			var dc = document.cookie;
			var prefix = name + "=";
			var begin = dc.indexOf("; " + prefix);
			if (begin == -1) {
				begin = dc.indexOf(prefix);
				if (begin != 0) { return null;}
			} else {
				begin += 2;
			}
			var end = document.cookie.indexOf(";", begin);
			if (end == -1) {
				end = dc.length;
			}
			return unescape(dc.substring(begin + prefix.length, end));
		},
		checkMobile: function(){
			var isMobile = false;
			var ua = navigator.userAgent.toLowerCase();
			var mobileDevice = new Array('iphone','ipod','ipad','android','blackberry','windows ce','nokia','webos','opera mini','sonyericsson','opera mobi','iemobile');
			for(var i=0;i<mobileDevice.length;i++){
				if(ua.indexOf(mobileDevice[i]) != -1){
					isMobile = true;
				}
			}
			return isMobile;
		},
		checkIos: function(){
			var iosDetect = false;
			var uaCheck = navigator.userAgent.toLowerCase();
			var iosDevice = new Array('iphone','ipod','ipad');
			for(var i=0;i<iosDevice.length;i++){
				if(uaCheck.indexOf(iosDevice[i]) != -1){
					iosDetect = true;
				}
			}
			return iosDetect;
		},
		checkAndroid: function(){
			var ua = navigator.userAgent.toLowerCase(),
			isAndroid;
			if(ua.indexOf('android') > -1) isAndroid = true;
			return isAndroid;
		},
		getURLParameter: function(sParam){
			var sPageURL = window.location.search.substring(1); 
			var sURLVariables = sPageURL.split('&'); 
			for (var i = 0; i < sURLVariables.length; i++) { 
				var sParameterName = sURLVariables[i].split('='); 
				if (sParameterName[0] == sParam) { 
					return sParameterName[1]; 
				} 
			} 
		},
		loadScript: function(src){
			var tag = document.createElement('script');
			tag.src = src;
			var firstScriptTag = document.getElementsByTagName('script')[0];
			firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
		},
		getIframeContent: function(id){
			var ifrm = document.getElementById(id);
			return ifrm.contentWindow || ifrm.contentDocument;
		},
		getRandomInt: function(min, max){
			return Math.floor(Math.random() * (max - min)) + min;
		},
		addComma: function(value){
			$(value).each(function(index){
				$(this).text($(this).text().split(/(?=(?:\d{3})+(?:\.|$))/g).join(','));
			});
		},
		getRatePer: function(min, max, value){
			var range = max - min;
			var tg = value - min;
			var per = tg / range;
			return per * 100;
		},
		getRateValue: function(min, max, per){
			var range = max - min;
			var tg = range * per;
			var value = tg + min;
			return value / 100;
		},
		inpuOnlyNumber: function(obj){
			$(obj).keydown(function(e){
			// Allow: backspace, delete, tab, escape, enter and .
			if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
				// Allow: Ctrl+A
				(e.keyCode == 65 && e.ctrlKey === true) || 
				// Allow: home, end, left, right
				(e.keyCode >= 35 && e.keyCode <= 39)) {

					// let it happen, don't do anything
					return;
			}

			// Ensure that it is a number and stop the keypress
			if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
				e.preventDefault();
			}
		});
		},
		checkIEVersion: function(nnnnn){
			var rv = -1;
			var rv2 = -1;
			if (navigator.appName == 'Microsoft Internet Explorer') {
				var ua = navigator.userAgent;
				var re = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
				if (re.exec(ua) != null){
					rv = parseFloat(RegExp.$1);
					if(rv == 7){
						var trident = navigator.userAgent.match(/Trident\/(\d)/i);
						var re2 = new RegExp("([0-9]{1,}[\.0-9]{0,})");
						if (re2.exec(trident) != null){
							rv2 = parseFloat(RegExp.$1)
							if(rv2 == 4){rv = 8}
							else if(rv2 == 5){rv = 9}
							else{}
						}
					}
					$('html').addClass('ie'+rv);
				}
			} else if(navigator.appName == "Netscape"){                       
				/// in IE 11 the navigator.appVersion says 'trident'
				/// in Edge the navigator.appVersion does not say trident
				if(navigator.appVersion.indexOf('Trident') === -1){ 
					rv = 12; 
				} else {
					rv = 11;	
					$('html').addClass('ie'+rv);
				} 
			} 
		}
	}
}());


/*
 * 설명   : frontScript 메서드 모음
 * 사용처 : document.ready 구문에 실행함수 탑재
 */
var frontScript = window.frontScript || (function(){
	return {
		init: function(){
			//공통 UI
			frontScript.baseUI($(document));
			
			//공통 체크
			frontScript.comCheck();
			
			//컨텐츠 스크립트 모음
			frontScript.comContents();
		},
		baseUI: function($this){
			/* 설명   : 전역 공통으로 사용하는 UI 
			   사용처 : 기본 전역으로 1번 실행하고 나중에 다른곳에서 재실행 할때 방지되어 있는 구조 */
			
			var _ = $this;
			
			//placeholder(공통 - IE9 이하 부터 실행)
			_.find('.input-base').placeholder();					
		},
		comCheck: function(){
			// ios check
			if(comUtil.checkIos() == false){ $("html").addClass("no-ios-device"); }  
			
			//모바일 check
			if(comUtil.checkMobile() == true){ $("html").addClass("is-mobile"); }
			
			//ie 버전체크
			comUtil.checkIEVersion();												  
		},
		nsOption: function(){
			var option = {
				bouncescroll: false,
				autohidemode: false,
				background: '#162c49',
				cursorcolor: '#173457',
				cursorwidth: '10px',
				cursorborderradius: '3px',
				zindex: 1,
				cursorborder: '0'
			}
			return option;
		},
		comContents: function(){
			//custom scroll
			if(!comUtil.checkMobile()){ 
				$('.cl-row1').niceScroll(frontScript.nsOption());
				$('body').niceScroll(frontScript.nsOption());
			}
			         
			if($('.select-base').length>0){
				// custom select
				jcf.setOptions('Select', {wrapNative: false, fakeDropInBody: false});
				// custom all
				jcf.replaceAll();
			}
			
			//탭메뉴(공통)
			$('.o-tab-menu').setTabMenu();	
			
			//팝업 ajax 호출
			if($('.ajax-popup-link').length > 0){
				$('.ajax-popup-link').magnificPopup({
					type: 'ajax',
					showCloseBtn: false,
					callbacks: {
						parseAjax: function(mfpResponse) {
							mfpResponse.data = $(mfpResponse.data).filter('.wrap-popup');
						},
						ajaxContentAdded: function() {
							//console.log('ajaxContentAdded = ', this.content);
						}
					}
				});
			}
			
			//팝업 닫기
			$(document).on('click', '.wrap-popup .popup-close', function (e) {
			  e.preventDefault();
			  $.magnificPopup.close();
			});
				
			//탭메뉴 - 서브
			$('.tab-cont-setting .tcs-sub a').on('click', function(e){
				var show = $(this).attr('href');
				$('.tcs-cont.tcs-on').removeClass('tcs-on');
				$('.tcs-cont.tcs-on').removeClass('tcs-on');
				$(show).addClass('tcs-on');
				$(this).closest('.tcs-inner').prev('.txt').text($(this).text());				
				$(this).closest('.tcs-inner').prev('.txt').trigger('click');
				$(this).closest('.tcs-sub').find('on').removeClass('on');
				$(this).closest('li').addClass('on');
				e.preventDefault();
			});
		}
	}	
}());

/*
 * document ready
 */
$(function(){	
	frontScript.init();
});