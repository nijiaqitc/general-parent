<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<body>
	<style>
table td {
	border: 1px solid;
	padding-left: 4px;
}

table th {
	text-align: center;
	background-color: #555555;
	color: white;
	height: 34px;
	line-height: 34px;
}

.zztable {
	border-spacing: 0px;
	margin-top: 5px;
	margin-bottom: 5px;
	word-wrap: break-word;
	word-break: break-all;
	font-size: 12px;
	line-height: 22px;
	color: rgb(0, 0, 0);
	font-family: arial, 宋体, sans-serif;
	font-style: normal;
	width: 100%;
	text-align: center;
	border: 1px solid #555555;
}

.leftTd {
	padding-top: 2px;
	padding-bottom: 2px;
	font-size: 12px;
	line-height: 22px;
	height: 22px;
	border-color: rgb(230, 230, 230);
}

.leftTd span {
	font-size: 12px;
	word-wrap: break-word;
	color: rgb(51, 51, 51);
	margin-top: 0px;
	margin-bottom: 0px;
	zoom: 1;
	height: 30px;
	display: block;
	line-height: 30px;
}

.trd {
	background-color: #F6F4F0;
}

.trf {
	background-color: #fff;
}

.tbc {
	background-color: #fbfdff;
}
</style>
	<div class="commonRight" style="height: 2140px;">
		<div class="panel panel-default"
			style="margin-bottom: 0px; height: 100%;">
			<div class="panel-body" style="height: 100%;">
				<div style="height: 20px; text-indent: 6px;">
					<div style="float: left;">
						<label>数值--字符对应表</label>
					</div>
					<div style="float: right; width: 260px;">
						<div class="input-group"></div>
					</div>
				</div>
				<hr style="margin-top: 8px; margin-bottom: 8px;">
				<table class="zztable">
					<thead>
						<tr>
							<th>数值</th>
							<th>字符</th>
							<th>数值</th>
							<th>字符</th>
							<th>数值</th>
							<th>字符</th>
							<th>数值</th>
							<th>字符</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="leftTd tbc"><span>32</span></td>
							<td class="leftTd"><span> </span></td>
							<td class="leftTd tbc"><span>33</span></td>
							<td class="leftTd"><span>!</span></td>
							<td class="leftTd tbc"><span>34</span></td>
							<td class="leftTd"><span>"</span></td>
							<td class="leftTd tbc"><span>35</span></td>
							<td class="leftTd"><span>#</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>36</span></td>
							<td class="leftTd"><span>$</span></td>
							<td class="leftTd tbc"><span>37</span></td>
							<td class="leftTd"><span>%</span></td>
							<td class="leftTd tbc"><span>38</span></td>
							<td class="leftTd"><span>&</span></td>
							<td class="leftTd tbc"><span>39</span></td>
							<td class="leftTd"><span>'</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>40</span></td>
							<td class="leftTd"><span>(</span></td>
							<td class="leftTd tbc"><span>41</span></td>
							<td class="leftTd"><span>)</span></td>
							<td class="leftTd tbc"><span>42</span></td>
							<td class="leftTd"><span>*</span></td>
							<td class="leftTd tbc"><span>43</span></td>
							<td class="leftTd"><span>+</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>44</span></td>
							<td class="leftTd"><span>,</span></td>
							<td class="leftTd tbc"><span>45</span></td>
							<td class="leftTd"><span>-</span></td>
							<td class="leftTd tbc"><span>46</span></td>
							<td class="leftTd"><span>.</span></td>
							<td class="leftTd tbc"><span>47</span></td>
							<td class="leftTd"><span>/</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>48</span></td>
							<td class="leftTd"><span>0</span></td>
							<td class="leftTd tbc"><span>49</span></td>
							<td class="leftTd"><span>1</span></td>
							<td class="leftTd tbc"><span>50</span></td>
							<td class="leftTd"><span>2</span></td>
							<td class="leftTd tbc"><span>51</span></td>
							<td class="leftTd"><span>3</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>52</span></td>
							<td class="leftTd"><span>4</span></td>
							<td class="leftTd tbc"><span>53</span></td>
							<td class="leftTd"><span>5</span></td>
							<td class="leftTd tbc"><span>54</span></td>
							<td class="leftTd"><span>6</span></td>
							<td class="leftTd tbc"><span>55</span></td>
							<td class="leftTd"><span>7</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>56</span></td>
							<td class="leftTd"><span>8</span></td>
							<td class="leftTd tbc"><span>57</span></td>
							<td class="leftTd"><span>9</span></td>
							<td class="leftTd tbc"><span>58</span></td>
							<td class="leftTd"><span>:</span></td>
							<td class="leftTd tbc"><span>59</span></td>
							<td class="leftTd"><span>;</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>60</span></td>
							<td class="leftTd"><span><</span></td>
							<td class="leftTd tbc"><span>61</span></td>
							<td class="leftTd"><span>=</span></td>
							<td class="leftTd tbc"><span>62</span></td>
							<td class="leftTd"><span>></span></td>
							<td class="leftTd tbc"><span>63</span></td>
							<td class="leftTd"><span>?</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>64</span></td>
							<td class="leftTd"><span>@</span></td>
							<td class="leftTd tbc"><span>65</span></td>
							<td class="leftTd"><span>A</span></td>
							<td class="leftTd tbc"><span>66</span></td>
							<td class="leftTd"><span>B</span></td>
							<td class="leftTd tbc"><span>67</span></td>
							<td class="leftTd"><span>C</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>68</span></td>
							<td class="leftTd"><span>D</span></td>
							<td class="leftTd tbc"><span>69</span></td>
							<td class="leftTd"><span>E</span></td>
							<td class="leftTd tbc"><span>70</span></td>
							<td class="leftTd"><span>F</span></td>
							<td class="leftTd tbc"><span>71</span></td>
							<td class="leftTd"><span>G</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>72</span></td>
							<td class="leftTd"><span>H</span></td>
							<td class="leftTd tbc"><span>73</span></td>
							<td class="leftTd"><span>I</span></td>
							<td class="leftTd tbc"><span>74</span></td>
							<td class="leftTd"><span>J</span></td>
							<td class="leftTd tbc"><span>75</span></td>
							<td class="leftTd"><span>K</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>76</span></td>
							<td class="leftTd"><span>L</span></td>
							<td class="leftTd tbc"><span>77</span></td>
							<td class="leftTd"><span>M</span></td>
							<td class="leftTd tbc"><span>78</span></td>
							<td class="leftTd"><span>N</span></td>
							<td class="leftTd tbc"><span>79</span></td>
							<td class="leftTd"><span>O</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>80</span></td>
							<td class="leftTd"><span>P</span></td>
							<td class="leftTd tbc"><span>81</span></td>
							<td class="leftTd"><span>Q</span></td>
							<td class="leftTd tbc"><span>82</span></td>
							<td class="leftTd"><span>R</span></td>
							<td class="leftTd tbc"><span>83</span></td>
							<td class="leftTd"><span>S</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>84</span></td>
							<td class="leftTd"><span>T</span></td>
							<td class="leftTd tbc"><span>85</span></td>
							<td class="leftTd"><span>U</span></td>
							<td class="leftTd tbc"><span>86</span></td>
							<td class="leftTd"><span>V</span></td>
							<td class="leftTd tbc"><span>87</span></td>
							<td class="leftTd"><span>W</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>88</span></td>
							<td class="leftTd"><span>X</span></td>
							<td class="leftTd tbc"><span>89</span></td>
							<td class="leftTd"><span>Y</span></td>
							<td class="leftTd tbc"><span>90</span></td>
							<td class="leftTd"><span>Z</span></td>
							<td class="leftTd tbc"><span>91</span></td>
							<td class="leftTd"><span>[</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>92</span></td>
							<td class="leftTd"><span>\</span></td>
							<td class="leftTd tbc"><span>93</span></td>
							<td class="leftTd"><span>]</span></td>
							<td class="leftTd tbc"><span>94</span></td>
							<td class="leftTd"><span>^</span></td>
							<td class="leftTd tbc"><span>95</span></td>
							<td class="leftTd"><span>_</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>96</span></td>
							<td class="leftTd"><span>`</span></td>
							<td class="leftTd tbc"><span>97</span></td>
							<td class="leftTd"><span>a</span></td>
							<td class="leftTd tbc"><span>98</span></td>
							<td class="leftTd"><span>b</span></td>
							<td class="leftTd tbc"><span>99</span></td>
							<td class="leftTd"><span>c</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>100</span></td>
							<td class="leftTd"><span>d</span></td>
							<td class="leftTd tbc"><span>101</span></td>
							<td class="leftTd"><span>e</span></td>
							<td class="leftTd tbc"><span>102</span></td>
							<td class="leftTd"><span>f</span></td>
							<td class="leftTd tbc"><span>103</span></td>
							<td class="leftTd"><span>g</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>104</span></td>
							<td class="leftTd"><span>h</span></td>
							<td class="leftTd tbc"><span>105</span></td>
							<td class="leftTd"><span>i</span></td>
							<td class="leftTd tbc"><span>106</span></td>
							<td class="leftTd"><span>j</span></td>
							<td class="leftTd tbc"><span>107</span></td>
							<td class="leftTd"><span>k</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>108</span></td>
							<td class="leftTd"><span>l</span></td>
							<td class="leftTd tbc"><span>109</span></td>
							<td class="leftTd"><span>m</span></td>
							<td class="leftTd tbc"><span>110</span></td>
							<td class="leftTd"><span>n</span></td>
							<td class="leftTd tbc"><span>111</span></td>
							<td class="leftTd"><span>o</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>112</span></td>
							<td class="leftTd"><span>p</span></td>
							<td class="leftTd tbc"><span>113</span></td>
							<td class="leftTd"><span>q</span></td>
							<td class="leftTd tbc"><span>114</span></td>
							<td class="leftTd"><span>r</span></td>
							<td class="leftTd tbc"><span>115</span></td>
							<td class="leftTd"><span>s</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>116</span></td>
							<td class="leftTd"><span>t</span></td>
							<td class="leftTd tbc"><span>117</span></td>
							<td class="leftTd"><span>u</span></td>
							<td class="leftTd tbc"><span>118</span></td>
							<td class="leftTd"><span>v</span></td>
							<td class="leftTd tbc"><span>119</span></td>
							<td class="leftTd"><span>w</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>120</span></td>
							<td class="leftTd"><span>x</span></td>
							<td class="leftTd tbc"><span>121</span></td>
							<td class="leftTd"><span>y</span></td>
							<td class="leftTd tbc"><span>122</span></td>
							<td class="leftTd"><span>z</span></td>
							<td class="leftTd tbc"><span>123</span></td>
							<td class="leftTd"><span>{</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>124</span></td>
							<td class="leftTd"><span>|</span></td>
							<td class="leftTd tbc"><span>125</span></td>
							<td class="leftTd"><span>}</span></td>
							<td class="leftTd tbc"><span>126</span></td>
							<td class="leftTd"><span>~</span></td>
							<td class="leftTd tbc"><span>161</span></td>
							<td class="leftTd"><span>¡</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>162</span></td>
							<td class="leftTd"><span>¢</span></td>
							<td class="leftTd tbc"><span>163</span></td>
							<td class="leftTd"><span>£</span></td>
							<td class="leftTd tbc"><span>164</span></td>
							<td class="leftTd"><span>¤</span></td>
							<td class="leftTd tbc"><span>165</span></td>
							<td class="leftTd"><span>¥</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>166</span></td>
							<td class="leftTd"><span>¦</span></td>
							<td class="leftTd tbc"><span>167</span></td>
							<td class="leftTd"><span>§</span></td>
							<td class="leftTd tbc"><span>168</span></td>
							<td class="leftTd"><span>¨</span></td>
							<td class="leftTd tbc"><span>169</span></td>
							<td class="leftTd"><span>©</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>170</span></td>
							<td class="leftTd"><span>ª</span></td>
							<td class="leftTd tbc"><span>171</span></td>
							<td class="leftTd"><span>«</span></td>
							<td class="leftTd tbc"><span>172</span></td>
							<td class="leftTd"><span>¬</span></td>
							<td class="leftTd tbc"><span>173</span></td>
							<td class="leftTd"><span>­</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>174</span></td>
							<td class="leftTd"><span>®</span></td>
							<td class="leftTd tbc"><span>175</span></td>
							<td class="leftTd"><span>¯</span></td>
							<td class="leftTd tbc"><span>176</span></td>
							<td class="leftTd"><span>°</span></td>
							<td class="leftTd tbc"><span>177</span></td>
							<td class="leftTd"><span>±</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>178</span></td>
							<td class="leftTd"><span>²</span></td>
							<td class="leftTd tbc"><span>179</span></td>
							<td class="leftTd"><span>³</span></td>
							<td class="leftTd tbc"><span>180</span></td>
							<td class="leftTd"><span>´</span></td>
							<td class="leftTd tbc"><span>181</span></td>
							<td class="leftTd"><span>µ</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>182</span></td>
							<td class="leftTd"><span>¶</span></td>
							<td class="leftTd tbc"><span>183</span></td>
							<td class="leftTd"><span>·</span></td>
							<td class="leftTd tbc"><span>184</span></td>
							<td class="leftTd"><span>¸</span></td>
							<td class="leftTd tbc"><span>185</span></td>
							<td class="leftTd"><span>¹</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>186</span></td>
							<td class="leftTd"><span>º</span></td>
							<td class="leftTd tbc"><span>187</span></td>
							<td class="leftTd"><span>»</span></td>
							<td class="leftTd tbc"><span>188</span></td>
							<td class="leftTd"><span>¼</span></td>
							<td class="leftTd tbc"><span>189</span></td>
							<td class="leftTd"><span>½</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>190</span></td>
							<td class="leftTd"><span>¾</span></td>
							<td class="leftTd tbc"><span>191</span></td>
							<td class="leftTd"><span>¿</span></td>
							<td class="leftTd tbc"><span>192</span></td>
							<td class="leftTd"><span>À</span></td>
							<td class="leftTd tbc"><span>193</span></td>
							<td class="leftTd"><span>Á</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>194</span></td>
							<td class="leftTd"><span>Â</span></td>
							<td class="leftTd tbc"><span>195</span></td>
							<td class="leftTd"><span>Ã</span></td>
							<td class="leftTd tbc"><span>196</span></td>
							<td class="leftTd"><span>Ä</span></td>
							<td class="leftTd tbc"><span>197</span></td>
							<td class="leftTd"><span>Å</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>200</span></td>
							<td class="leftTd"><span>È</span></td>
							<td class="leftTd tbc"><span>201</span></td>
							<td class="leftTd"><span>É</span></td>
							<td class="leftTd tbc"><span>202</span></td>
							<td class="leftTd"><span>Ê</span></td>
							<td class="leftTd tbc"><span>203</span></td>
							<td class="leftTd"><span>Ë</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>204</span></td>
							<td class="leftTd"><span>Ì</span></td>
							<td class="leftTd tbc"><span>205</span></td>
							<td class="leftTd"><span>Í</span></td>
							<td class="leftTd tbc"><span>206</span></td>
							<td class="leftTd"><span>Î</span></td>
							<td class="leftTd tbc"><span>207</span></td>
							<td class="leftTd"><span>Ï</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>208</span></td>
							<td class="leftTd"><span>Ð</span></td>
							<td class="leftTd tbc"><span>209</span></td>
							<td class="leftTd"><span>Ñ</span></td>
							<td class="leftTd tbc"><span>210</span></td>
							<td class="leftTd"><span>Ò</span></td>
							<td class="leftTd tbc"><span>211</span></td>
							<td class="leftTd"><span>Ó</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>212</span></td>
							<td class="leftTd"><span>Ô</span></td>
							<td class="leftTd tbc"><span>213</span></td>
							<td class="leftTd"><span>Õ</span></td>
							<td class="leftTd tbc"><span>214</span></td>
							<td class="leftTd"><span>Ö</span></td>
							<td class="leftTd tbc"><span>215</span></td>
							<td class="leftTd"><span>×</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>216</span></td>
							<td class="leftTd"><span>Ø</span></td>
							<td class="leftTd tbc"><span>217</span></td>
							<td class="leftTd"><span>Ù</span></td>
							<td class="leftTd tbc"><span>218</span></td>
							<td class="leftTd"><span>Ú</span></td>
							<td class="leftTd tbc"><span>219</span></td>
							<td class="leftTd"><span>Û</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>220</span></td>
							<td class="leftTd"><span>Ü</span></td>
							<td class="leftTd tbc"><span>221</span></td>
							<td class="leftTd"><span>Ý</span></td>
							<td class="leftTd tbc"><span>222</span></td>
							<td class="leftTd"><span>Þ</span></td>
							<td class="leftTd tbc"><span>223</span></td>
							<td class="leftTd"><span>ß</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>224</span></td>
							<td class="leftTd"><span>à</span></td>
							<td class="leftTd tbc"><span>225</span></td>
							<td class="leftTd"><span>á</span></td>
							<td class="leftTd tbc"><span>226</span></td>
							<td class="leftTd"><span>â</span></td>
							<td class="leftTd tbc"><span>227</span></td>
							<td class="leftTd"><span>ã</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>228</span></td>
							<td class="leftTd"><span>ä</span></td>
							<td class="leftTd tbc"><span>229</span></td>
							<td class="leftTd"><span>å</span></td>
							<td class="leftTd tbc"><span>230</span></td>
							<td class="leftTd"><span>æ</span></td>
							<td class="leftTd tbc"><span>231</span></td>
							<td class="leftTd"><span>ç</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>232</span></td>
							<td class="leftTd"><span>è</span></td>
							<td class="leftTd tbc"><span>233</span></td>
							<td class="leftTd"><span>é</span></td>
							<td class="leftTd tbc"><span>234</span></td>
							<td class="leftTd"><span>ê</span></td>
							<td class="leftTd tbc"><span>235</span></td>
							<td class="leftTd"><span>ë</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>236</span></td>
							<td class="leftTd"><span>ì</span></td>
							<td class="leftTd tbc"><span>237</span></td>
							<td class="leftTd"><span>í</span></td>
							<td class="leftTd tbc"><span>238</span></td>
							<td class="leftTd"><span>î</span></td>
							<td class="leftTd tbc"><span>239</span></td>
							<td class="leftTd"><span>ï</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>240</span></td>
							<td class="leftTd"><span>ð</span></td>
							<td class="leftTd tbc"><span>241</span></td>
							<td class="leftTd"><span>ñ</span></td>
							<td class="leftTd tbc"><span>242</span></td>
							<td class="leftTd"><span>ò</span></td>
							<td class="leftTd tbc"><span>243</span></td>
							<td class="leftTd"><span>ó</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>244</span></td>
							<td class="leftTd"><span>ô</span></td>
							<td class="leftTd tbc"><span>245</span></td>
							<td class="leftTd"><span>õ</span></td>
							<td class="leftTd tbc"><span>246</span></td>
							<td class="leftTd"><span>ö</span></td>
							<td class="leftTd tbc"><span>247</span></td>
							<td class="leftTd"><span>÷</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>248</span></td>
							<td class="leftTd"><span>ø</span></td>
							<td class="leftTd tbc"><span>249</span></td>
							<td class="leftTd"><span>ù</span></td>
							<td class="leftTd tbc"><span>250</span></td>
							<td class="leftTd"><span>ú</span></td>
							<td class="leftTd tbc"><span>251</span></td>
							<td class="leftTd"><span>û</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>252</span></td>
							<td class="leftTd"><span>ü</span></td>
							<td class="leftTd tbc"><span>253</span></td>
							<td class="leftTd"><span>ý</span></td>
							<td class="leftTd tbc"><span>254</span></td>
							<td class="leftTd"><span>þ</span></td>
							<td class="leftTd tbc"><span>255</span></td>
							<td class="leftTd"><span>ÿ</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>256</span></td>
							<td class="leftTd"><span>Ā</span></td>
							<td class="leftTd tbc"><span>257</span></td>
							<td class="leftTd"><span>ā</span></td>
							<td class="leftTd tbc"><span>258</span></td>
							<td class="leftTd"><span>Ă</span></td>
							<td class="leftTd tbc"><span>259</span></td>
							<td class="leftTd"><span>ă</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>260</span></td>
							<td class="leftTd"><span>Ą</span></td>
							<td class="leftTd tbc"><span>261</span></td>
							<td class="leftTd"><span>ą</span></td>
							<td class="leftTd tbc"><span>262</span></td>
							<td class="leftTd"><span>Ć</span></td>
							<td class="leftTd tbc"><span>263</span></td>
							<td class="leftTd"><span>ć</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>264</span></td>
							<td class="leftTd"><span>Ĉ</span></td>
							<td class="leftTd tbc"><span>265</span></td>
							<td class="leftTd"><span>ĉ</span></td>
							<td class="leftTd tbc"><span>266</span></td>
							<td class="leftTd"><span>Ċ</span></td>
							<td class="leftTd tbc"><span>267</span></td>
							<td class="leftTd"><span>ċ</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>268</span></td>
							<td class="leftTd"><span>Č</span></td>
							<td class="leftTd tbc"><span>269</span></td>
							<td class="leftTd"><span>č</span></td>
							<td class="leftTd tbc"><span>270</span></td>
							<td class="leftTd"><span>Ď</span></td>
							<td class="leftTd tbc"><span>271</span></td>
							<td class="leftTd"><span>ď</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>272</span></td>
							<td class="leftTd"><span>Đ</span></td>
							<td class="leftTd tbc"><span>273</span></td>
							<td class="leftTd"><span>đ</span></td>
							<td class="leftTd tbc"><span>274</span></td>
							<td class="leftTd"><span>Ē</span></td>
							<td class="leftTd tbc"><span>275</span></td>
							<td class="leftTd"><span>ē</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>276</span></td>
							<td class="leftTd"><span>Ĕ</span></td>
							<td class="leftTd tbc"><span>277</span></td>
							<td class="leftTd"><span>ĕ</span></td>
							<td class="leftTd tbc"><span>278</span></td>
							<td class="leftTd"><span>Ė</span></td>
							<td class="leftTd tbc"><span>279</span></td>
							<td class="leftTd"><span>ė</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>280</span></td>
							<td class="leftTd"><span>Ę</span></td>
							<td class="leftTd tbc"><span>281</span></td>
							<td class="leftTd"><span>ę</span></td>
							<td class="leftTd tbc"><span>282</span></td>
							<td class="leftTd"><span>Ě</span></td>
							<td class="leftTd tbc"><span>283</span></td>
							<td class="leftTd"><span>ě</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>284</span></td>
							<td class="leftTd"><span>Ĝ</span></td>
							<td class="leftTd tbc"><span>285</span></td>
							<td class="leftTd"><span>ĝ</span></td>
							<td class="leftTd tbc"><span>286</span></td>
							<td class="leftTd"><span>Ğ</span></td>
							<td class="leftTd tbc"><span>287</span></td>
							<td class="leftTd"><span>ğ</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>288</span></td>
							<td class="leftTd"><span>Ġ</span></td>
							<td class="leftTd tbc"><span>289</span></td>
							<td class="leftTd"><span>ġ</span></td>
							<td class="leftTd tbc"><span>290</span></td>
							<td class="leftTd"><span>Ģ</span></td>
							<td class="leftTd tbc"><span>291</span></td>
							<td class="leftTd"><span>ģ</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>292</span></td>
							<td class="leftTd"><span>Ĥ</span></td>
							<td class="leftTd tbc"><span>293</span></td>
							<td class="leftTd"><span>ĥ</span></td>
							<td class="leftTd tbc"><span>294</span></td>
							<td class="leftTd"><span>Ħ</span></td>
							<td class="leftTd tbc"><span>295</span></td>
							<td class="leftTd"><span>ħ</span></td>
						</tr>
						<tr>
							<td class="leftTd tbc"><span>296</span></td>
							<td class="leftTd"><span>Ĩ</span></td>
							<td class="leftTd tbc"><span>297</span></td>
							<td class="leftTd"><span>ĩ</span></td>
							<td class="leftTd tbc"><span>298</span></td>
							<td class="leftTd"><span>Ī</span></td>
							<td class="leftTd tbc"><span>299</span></td>
							<td class="leftTd"><span>ī</span></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>