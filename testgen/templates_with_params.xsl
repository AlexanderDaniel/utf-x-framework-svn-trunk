<?xml version="1.0" encoding="UTF-8"?>
<!--
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  $Id$
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  Purpose: XSLT stylesheet to test and demonstrate TestGenerator  
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  Copyright(C) 2006 UTF-X Developers.
  This program is free software; you can redistribute it and/or modify
  it under the terms of the GNU General Public License v2 as published
  by the Free Software Foundation (http://www.gnu.org/licenses/gpl.txt)
  This program is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  General Public License for more details.
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">


  <xsl:template name="named-template-with-param">
    <xsl:param name="a"/>
    
    <a>
      <xsl:copy-of select="$a"/>
    </a>
  </xsl:template>


  <xsl:template name="named-template-with-params">
    <xsl:param name="a"/>
    <xsl:param name="b"/>
    <xsl:param name="c"/>
    
    <a>
      <xsl:copy-of select="$a"/>
    </a>
    <b>
      <xsl:copy-of select="$b"/>
    </b>
    <c>
      <xsl:copy-of select="$c"/>
    </c>
  </xsl:template>
  

  <xsl:template name="named-template-with-param-and-select">
    <xsl:param name="a" select="2006"/>
    
    <a>
      <xsl:copy-of select="$a"/>
    </a>
  </xsl:template>



  <xsl:template name="named-template-with-param-and-childs">
    <xsl:param name="a">
      <tree></tree>
    </xsl:param>
    
    <a>
      <xsl:copy-of select="$a"/>
    </a>
  </xsl:template>

  
</xsl:stylesheet>
