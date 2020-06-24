function EQ_GET_DATA(){ 
	  var ret = {
"name": " supermarket_java", "value":792, 
"prmetrics":{"5":1,"6":1,"7":1,"8":1,"9":1,"10":1,"11":1},
"prmetricvalues":{"5":0,"6":6,"7":0,"8":792,"9":9,"10":1,"11":40},
"children": [ {
"name": "", "value":792, 
"pmetrics":{"4":2,"12":1,"13":1,"14":2,"3":1,"1":2,"0":2,"6":2,"8":2,"2":1,"15":1,"16":2},
"pmetricvalues":{"4":2,"12":0,"13":0,"14":6,"3":1,"1":2,"0":2,"17":0.0,"6":6,"18":0.0,"8":792,"19":1.0,"2":1,"15":0,"16":219},
"children":[
{
"name": "SQL_helper","key": "m","value":81, 
"metrics":{"20":1,"21":1,"22":1,"23":1,"24":2,"25":1,"26":1,"0":1,"27":1,"28":1,"29":1,"30":1,"31":1,"32":1,"33":1,"34":1,"35":1,"36":1,"16":1,"37":1,"4":2,"8":2,"2":1,"3":1,"1":1},
"metricvalues":{"20":0,"21":0,"22":2,"23":0.0,"24":75,"25":0,"26":1,"0":1,"27":5,"28":25,"29":0.0,"30":0.0,"31":3,"32":0.1,"33":0,"34":20,"35":0,"36":0,"16":11,"37":0,"4":2,"8":81,"2":1,"3":1,"1":1}
},
{
"name": "Supermarket","key": "p","value":155, 
"metrics":{"20":1,"21":2,"22":1,"23":1,"24":2,"25":1,"26":1,"0":3,"27":1,"28":3,"29":5,"30":4,"31":1,"32":3,"33":1,"34":2,"35":1,"36":1,"16":2,"37":1,"4":2,"8":2,"2":1,"3":3,"1":3},
"metricvalues":{"20":5,"21":2,"22":9,"23":0.0,"24":145,"25":0,"26":1,"0":3,"27":18,"28":139,"29":1.0,"30":0.949,"31":0,"32":0.711,"33":5,"34":59,"35":1,"36":0,"16":40,"37":0,"4":2,"8":155,"2":1,"3":3,"1":3}
},
{
"name": "Parameters","key": "l","value":262, 
"metrics":{"20":1,"21":1,"22":1,"23":1,"24":2,"25":1,"26":1,"0":3,"27":3,"28":1,"29":5,"30":4,"31":1,"32":2,"33":1,"34":1,"35":1,"36":1,"16":3,"37":1,"4":3,"8":2,"2":1,"3":2,"1":3},
"metricvalues":{"20":0,"21":0,"22":13,"23":0.0,"24":248,"25":0,"26":1,"0":3,"27":37,"28":44,"29":0.953,"30":0.946,"31":0,"32":0.669,"33":0,"34":40,"35":0,"36":0,"16":84,"37":0,"4":3,"8":262,"2":1,"3":2,"1":3}
},
{
"name": "Stats_writer","key": "o","value":90, 
"metrics":{"20":1,"21":1,"22":1,"23":1,"24":2,"25":1,"26":1,"0":1,"27":1,"28":1,"29":1,"30":1,"31":1,"32":1,"33":1,"34":1,"35":1,"36":1,"16":1,"37":1,"4":2,"8":2,"2":1,"3":1,"1":1},
"metricvalues":{"20":0,"21":0,"22":0,"23":0.0,"24":89,"25":0,"26":1,"0":1,"27":4,"28":22,"29":0.0,"30":0.0,"31":0,"32":0.417,"33":0,"34":20,"35":0,"36":0,"16":13,"37":0,"4":2,"8":90,"2":1,"3":1,"1":1}
},
{
"name": "Holidays","key": "k","value":132, 
"metrics":{"20":1,"21":1,"22":1,"23":1,"24":2,"25":1,"26":1,"0":2,"27":1,"28":1,"29":1,"30":1,"31":1,"32":1,"33":1,"34":1,"35":1,"36":1,"16":2,"37":1,"4":2,"8":2,"2":1,"3":1,"1":2},
"metricvalues":{"20":0,"21":0,"22":1,"23":0.0,"24":130,"25":0,"26":1,"0":2,"27":15,"28":20,"29":0.0,"30":0.0,"31":0,"32":0.0,"33":0,"34":19,"35":0,"36":0,"16":45,"37":0,"4":2,"8":132,"2":1,"3":1,"1":2}
},
{
"name": "Shopper","key": "n","value":72, 
"metrics":{"20":1,"21":1,"22":1,"23":1,"24":2,"25":1,"26":1,"0":2,"27":1,"28":1,"29":2,"30":4,"31":1,"32":2,"33":1,"34":1,"35":1,"36":1,"16":2,"37":1,"4":2,"8":2,"2":1,"3":2,"1":2},
"metricvalues":{"20":1,"21":1,"22":10,"23":0.0,"24":62,"25":0,"26":1,"0":2,"27":14,"28":25,"29":0.667,"30":0.883,"31":0,"32":0.679,"33":1,"34":21,"35":0,"36":0,"16":26,"37":0,"4":2,"8":72,"2":1,"3":2,"1":2}
}
]
}]
 }
;
return ret;
}
var EQ_METRIC_MAP = {};
EQ_METRIC_MAP["C3"] =0;
EQ_METRIC_MAP["Complexity"] =1;
EQ_METRIC_MAP["Coupling"] =2;
EQ_METRIC_MAP["Lack of Cohesion"] =3;
EQ_METRIC_MAP["Size"] =4;
EQ_METRIC_MAP["Number of Highly Problematic Classes"] =5;
EQ_METRIC_MAP["Number of Entities"] =6;
EQ_METRIC_MAP["Number of Problematic Classes"] =7;
EQ_METRIC_MAP["Class Lines of Code"] =8;
EQ_METRIC_MAP["Number of External Packages"] =9;
EQ_METRIC_MAP["Number of Packages"] =10;
EQ_METRIC_MAP["Number of External Entities"] =11;
EQ_METRIC_MAP["Efferent Coupling"] =12;
EQ_METRIC_MAP["Number of Interfaces"] =13;
EQ_METRIC_MAP["Number of Classes"] =14;
EQ_METRIC_MAP["Afferent Coupling"] =15;
EQ_METRIC_MAP["Weighted Method Count"] =16;
EQ_METRIC_MAP["Normalized Distance"] =17;
EQ_METRIC_MAP["Abstractness"] =18;
EQ_METRIC_MAP["Instability"] =19;
EQ_METRIC_MAP["Coupling Between Object Classes"] =20;
EQ_METRIC_MAP["Access to Foreign Data"] =21;
EQ_METRIC_MAP["Number of Fields"] =22;
EQ_METRIC_MAP["Specialization Index"] =23;
EQ_METRIC_MAP["Class-Methods Lines of Code"] =24;
EQ_METRIC_MAP["Number of Children"] =25;
EQ_METRIC_MAP["Depth of Inheritance Tree"] =26;
EQ_METRIC_MAP["Number of Methods"] =27;
EQ_METRIC_MAP["Response For a Class"] =28;
EQ_METRIC_MAP["Lack of Tight Class Cohesion"] =29;
EQ_METRIC_MAP["Lack of Cohesion of Methods"] =30;
EQ_METRIC_MAP["Number of Static Fields"] =31;
EQ_METRIC_MAP["Lack of Cohesion Among Methods(1-CAM)"] =32;
EQ_METRIC_MAP["CBO App"] =33;
EQ_METRIC_MAP["Simple Response For a Class"] =34;
EQ_METRIC_MAP["Number of Static Methods"] =35;
EQ_METRIC_MAP["CBO Lib"] =36;
EQ_METRIC_MAP["Number of Overridden Methods"] =37;
var EQ_SELECTED_CLASS_METRIC 		= "C3";
var EQ_SELECTED_PACKAGE_METRIC 	= "C3";
var EQ_SELECTED_PROJECT_METRIC 	= "Class Lines of Code";
var EQ_CLASS_METRIC_INDEX 	= EQ_METRIC_MAP[EQ_SELECTED_CLASS_METRIC];
var EQ_PACKAGE_METRIC_INDEX	= EQ_METRIC_MAP[EQ_SELECTED_PACKAGE_METRIC];
var EQ_PROJECT_METRIC_INDEX 	= EQ_METRIC_MAP[EQ_SELECTED_PROJECT_METRIC];
var EQ_COLOR_OF_LEVELS = ["#1F77B4","#007F24","#62BF18","#FFC800","#FF5B13","#E50000"];
var EQ_CLASS_METRICS = ["C3","Complexity","Coupling","Lack of Cohesion","Size","Class Lines of Code","Weighted Method Count","Coupling Between Object Classes","Access to Foreign Data","Number of Fields","Specialization Index","Class-Methods Lines of Code","Number of Children","Depth of Inheritance Tree","Number of Methods","Response For a Class","Lack of Tight Class Cohesion","Lack of Cohesion of Methods","Number of Static Fields","Lack of Cohesion Among Methods(1-CAM)","CBO App","Simple Response For a Class","Number of Static Methods","CBO Lib","Number of Overridden Methods"];
var EQ_PACKAGE_METRICS = ["C3","Complexity","Coupling","Lack of Cohesion","Size","Number of Entities","Class Lines of Code","Efferent Coupling","Number of Interfaces","Number of Classes","Afferent Coupling","Weighted Method Count","Normalized Distance","Abstractness","Instability"];
var EQ_PROJECT_METRICS = ["Number of Highly Problematic Classes","Number of Entities","Number of Problematic Classes","Class Lines of Code","Number of External Packages","Number of Packages","Number of External Entities"];
function EQ_GET_COLOR(d) {
if(d.metrics)
return EQ_COLOR_OF_LEVELS[d.metrics[EQ_CLASS_METRIC_INDEX]];
if(d.pmetrics)
return EQ_COLOR_OF_LEVELS[d.pmetrics[EQ_PACKAGE_METRIC_INDEX]];
if(d.prmetrics)
return EQ_COLOR_OF_LEVELS[d.prmetrics[EQ_PROJECT_METRIC_INDEX]];
return EQ_COLOR_OF_LEVELS[0];
}
