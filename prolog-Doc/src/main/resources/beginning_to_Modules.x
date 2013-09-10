<!DOCTYPE html>
<html>
<head>
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>

<style type="text/css">
body { margin:0; padding:0; }

#r3 { position:relative; top:30px; margin: 25px; background-color:#ffffff; }
#inner { position:relative; margin: 0px; background-color:#ffffff; font-family:verdana;padding:40px;border-radius:10px;border:0px solid #a7bec6; box-shadow:1px 1px 0px #666;}

#container {width:95%; background-color:white; border-radius:10px; margin:50px auto }
#b1 {float: middle; width: 96%; height: 100px; font-family:verdana;padding:40px;border-radius:1px;border:2px solid #a7bec6; background-color:#ffffff; outline: 1px }
#b2 {float: left; width: 13%; outline: 1px; overflow-x:scroll; font-family:verdana;padding:40px;border-radius:10px;border:0px solid #a7bec6; background-color:#cbeefb;}
#b3 {float: middle; margin-left: 18%; width: 78%; outline: 1px; font-family:verdana;padding:40px;border-radius:2px;border:0px solid #a7bec6; background-color:#ffffff;}
#b5 {float: left; width: 99.5%; height: 100px; outline: 1px; border-radius:5px;
	font-family:verdana;border:2px solid #a7bec6; background-color:#ffffff;
	 }

#body { background-color:#cbeefb;}


#navi {
	float: middle; position:fixed; width: 99.5%; outline: 1px; font-family:verdana;
	border-radius:2px;border:0px solid #a7bec6; background-color:#cbeefb; z-index: 1000;
	border-radius:1px;border:2px solid #9ae1fb; 	
	}

#odd{
    background-color: white;   
}
#even{
    background-color: #eef3f3;   
}

#table{
    background-color: #eef3f3;  width:80%;  border-radius:10px; padding: 0px;
}

#row{
	padding: 0px; padding-left:20px; width:1; height:5px;
}
#codeblock{
	padding:10px; padding-left:30px; padding-top:0px; border-radius:10px;border:2px solid #a7bec6; background-color:#2f2f2f;  overflow: auto;
}

#code{
	color=#efecde;
}

#button{
	font-family:verdana;
	font-size: 18px;
}

#navi a { padding-left:15px; padding-right:15px; float:left; }


a.anchor{display: block; position: relative; top: -200px; visibility: hidden;}

</style>

<style type="text/css">a {text-decoration: none} 

<!--
a:hover 
{
background-color: rgb(200, 220, 220);
}
//-->

</style>
</head>
<body id='body' link="#067494" vlink="#1e7790" alink="#12c2c0">
	
	<script type="text/javascript">
	function hidecode (codeblock){
		if (document.getElementById(codeblock).style.display=="none"){
			document.getElementById(codeblock).style.display="";
		}else{
			document.getElementById(codeblock).style.display="none";
		}
	}
	</script>	
	
<div id="navi" class="menuBar">
	&nbsp;&nbsp;<a href="#TOP">Back To Top</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
	<a href="ModuleIndex.html">Module Index</a> 
	<a href="PredicateIndex.html">Predicate Index</a>
	<a href="UndocumentedPredicateIndex.html">Undocumented Predicates Index</a>
	<a href="EmphasizedPredicateIndex.html">Emphasized Predicates Index</a>
	<a href="CautionModuleIndex.html">Caution Module Index</a>
	 &nbsp;&nbsp;&nbsp; <a>|</a> &nbsp;&nbsp;&nbsp;
	
	<a href="#MODULE_INFO">Module Information</a>
	<a href="#PREDICATES">Predicates</a>
	<a href="#SPDET">spdet</a> 
</div>	

<div id="b1" class="box">
<h1 align="center"><a href="
