<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ attribute name="pageNo" required="true" rtexprvalue="true" %>
<%@ attribute name="pageCount" required="true" rtexprvalue="true" %>
<%@ attribute name="scriptName" required="true" rtexprvalue="true" %>
<%@ attribute name="paramHtml" required="true" rtexprvalue="true" %>

<c:set var="maxPageCount" scope="request">10</c:set>
<c:set var="halfPageCount" scope="request">5</c:set>

<c:if test="${pageNo > 1}">
	<a href="${scriptName}?${paramHtml }&pageNo=${pageNo - 1}">up</a>
</c:if>

<c:choose>
	<c:when test="${pageCount < maxPageCount + 1}">
		<c:forEach var="currentPage" begin="1" end="${pageCount}">
			<c:choose>	
				<c:when test="${currentPage == pageNo}">
					<span id="Cur">${currentPage }</span>
				</c:when>
				<c:otherwise>
					<a href="${scriptName}?${paramHtml}&pageNo=${currentPage}">${currentPage}</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<c:if test="${pageNo - halfPageCount < 0}">
		  <c:forEach var="currentPage1" begin="1" end="${maxPageCount}">
	 			<c:choose>	
					<c:when test="${currentPage1 == pageNo}">
						<span id="Cur">${currentPage1 }</span>
					</c:when>
					<c:otherwise>
						<a href="${scriptName}?${paramHtml}&pageNo=${currentPage1}">${currentPage1}</a>
					</c:otherwise>
				</c:choose>
	 		</c:forEach>
		</c:if>
		
		<c:if test="${(pageCount - pageNo) < halfPageCount}">
		    <c:forEach var="currentPage2" begin="${pageCount - maxPageCount + 1}" end="${pageCount}">
	 			<c:choose>	
					<c:when test="${currentPage2 == pageNo}">
						<span id="Cur">${currentPage2 }</span>
					</c:when>
					<c:otherwise>
						<a href="${scriptName }?${paramHtml }&pageNo=${currentPage2 }">${currentPage2}</a>
					</c:otherwise>
				</c:choose>
	 		</c:forEach>
		</c:if>
		
		<c:if test="${(pageNo > halfPageCount - 1) && ((pageCount - pageNo) > halfPageCount - 1)}">
			<c:forEach var="currentPage3" begin="${pageNo - halfPageCount + 1}" end="${pageNo + halfPageCount}">
	 			<c:choose>	
					<c:when test="${currentPage3 == pageNo}">
						<span id="Cur">${currentPage3 }</span>
					</c:when>
					<c:otherwise>
						<a href="${scriptName }?${paramHtml }&pageNo=${currentPage3 }">${currentPage3}</a>
					</c:otherwise>
				</c:choose>
	 		</c:forEach>
		</c:if>
		
		
	</c:otherwise>
</c:choose>

<c:if test="${pageNo - pageCount < 0}">
	<a href="${ scriptName}?${paramHtml }&pageNo=${pageNo + 1}">下一页</a>
</c:if>

分页:<b>${pageNo}</b>/<b>${pageCount}</b>页&nbsp;
