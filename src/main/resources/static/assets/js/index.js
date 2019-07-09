// 设置一些全局变量，既然是全局变量，总要有标识读吧，命名就用全大写下划线分隔（ps：全局变量是有限较少个数）
var GLOBAL_DEMO = "i am GLOBAL_DEMO/我是个示例";


// JQuery初始化
jQuery(function () {
    var windowHeight = $(window).height() - 100;
    $(".al-main").height(windowHeight);

    var windowWidth = $(window).width();
    if( windowWidth <= 1200)
        jQuery(".sec-main").toggleClass('menu-collapsed');

    //先隐藏界面内容，等权限验证后再开启
    //jQuery(".al-sidebar").hide();

	jQuery("[data-toggle='btn']").click(function(){
		jQuery(".sec-main").toggleClass('menu-collapsed');
	});

	// 截取菜单 <a>表情按的点击事件
	jQuery(".link").on('click', function (event) {
		var attrurl = "";
		event.preventDefault();

		var $this = jQuery(this);		
		jQuery("li .selected").removeClass('selected');
		if($this.closest('ul').hasClass('al-sidebar-sublist')) {
			$this.parent().addClass('selected');
		}
		links = $this.parents('li');
		var otherLinks = jQuery('.al-sidebar .al-sidebar-list li').not(links);
		otherLinks.removeClass('selected');
		links.addClass('selected');

		attrurl = $this.attr("href");
		url2view(attrurl);
	});

	// TODO，
	// @author ChenWM   
	// 我是后端的，负责后端接口权限就行了；前端菜单按钮显示我就不负责，就不写这部分代码了

});

// 初始化方法
function init(){
    jQuery(".al-sidebar").show();
}

// 菜单对应的html页面加载
function url2view(attrurl) {
	jQuery.ajax({
		url: attrurl,
		dataType: 'html',
		success: function (data) {
			jQuery(".al-main").html(data);
		},
		error: function () {
			alert("系统错误");
		}
	});
}

