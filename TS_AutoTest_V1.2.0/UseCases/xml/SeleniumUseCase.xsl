<?xml version="1.0" encoding="gb2312"?>
<!DOCTYPE xsl:stylesheet [ <!ENTITY nbsp "&#160;">]>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="1.0" encoding="gb2312"
		indent="yes" />
	<xsl:template match="/">
		<HTML>
			<head>
			</head>
			<BODY topmargin="0">
				<table id="Table_01" width="100%" height="49" border="0"
					cellpadding="0" cellspacing="0">
					<tr>
						<td width="687" height="49">
							<table width="687" height="49" >
								<tr>
									<td></td>
								</tr>
							</table>
						</td>
						<td width="100%" align="right">
							<font
								style="font-family:Arial;font-size:12px;position:relative;right:130px;">
								致友微博 自动化测试演示
								<BR />
								 By Kiven
							</font>
						</td>
					</tr>
				</table>
				<xsl:for-each select="SUITE/TEST">
					<br />
					<font color="green">
						<center>
							用例名称:
							<xsl:value-of select="@name" />
						</center>
					</font>
					<br />
					<xsl:for-each select="STEPS">
						<br />
						<TABLE width="90%" cellspacing="1" cellpadding="1" ALIGN="CENTER">
							<TBODY>
								<TR bgcolor="#00e2df">
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</TR>
								<TR bgcolor="#d7e2da">
									<TH>
										<font size="2.5">步骤编号</font>
									</TH>
									<TH>
										<font size="2.5">操作元素</font>
									</TH>
									<TH>
										<font size="2.5">操作步骤</font>
									</TH>
									<TH>
										<font size="2.5">期望结果</font>
									</TH>
									<TH>
										<font size="2.5">实际结果</font>
									</TH>
								</TR>
								<xsl:for-each select="STEP">
									<TR>
										<xsl:if test="position() mod 2 = 1">
											<xsl:attribute name="style">background-color:#fffffb</xsl:attribute>
										</xsl:if>
										<xsl:if test="position() mod 2 = 0">
											<xsl:attribute name="style">background-color:#72baa7</xsl:attribute>
										</xsl:if>
										<TD>
											<font size="2.5">
												<xsl:value-of select="@index" />
											</font>
										</TD>
										<TD>
											<font size="2.5">
											<xsl:value-of select="@type" />
											</font>
										</TD>
										<TD>
											<font size="2.5">
												<xsl:value-of select="@name" />
											</font>
										</TD>
										<TD>
											<font size="2.5">
												<xsl:value-of select="@verify" />
											</font>
										</TD>
										<TD>
											<font size="2.5">
												<xsl:value-of select="@result" />
											</font>
										</TD>
									</TR>
								</xsl:for-each>
								<!--step -->
							</TBODY>
						</TABLE>
					</xsl:for-each>
					<!--STEPS -->
					<br />
					<br />
				</xsl:for-each>
			</BODY>
		</HTML>
	</xsl:template>
</xsl:stylesheet>