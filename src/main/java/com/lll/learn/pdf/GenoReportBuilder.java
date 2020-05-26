package com.lll.learn.pdf;

import cn.hutool.core.date.DateUtil;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;
import com.lll.learn.data.ReportBean;
import com.lll.learn.pdf.event.HeaderTextEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author: laoliangliang
 * @description:
 * @create: 2020/5/20 8:34
 **/
public class GenoReportBuilder extends ReportBuilder {

    @Override
    public void initPdf(String outPath) throws IOException {
        super.initPdf(outPath);
    }

    @Override
    public GenoReportBuilder addIndex() {
        Image indexImage = new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/成人纸质报告-54.png")));
        Image introductionImage = new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/gene.png")));
        Image honorImage = new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/honor.png")));

        doc.add(indexImage);
        pdf.addNewPage(2).flush();
        doc.add(introductionImage);
        doc.add(honorImage);
        return this;
    }

    @Override
    public GenoReportBuilder addHello() {
        ReportBean.IndexBean index = reportBean.getIndex();

        Div div = new Div();
        div.setWidth(UnitValue.createPercentValue(100));
        div.setHeight(UnitValue.createPercentValue(100));
        div.setHorizontalAlignment(HorizontalAlignment.CENTER);
        Paragraph p1 = new Paragraph();
        p1.setHorizontalAlignment(HorizontalAlignment.CENTER);
        p1.setMaxWidth(UnitValue.createPercentValue(75));
        p1.setMarginTop(180f);
        p1.setCharacterSpacing(0.4f);
        Style large = new Style();
        large.setFontSize(22);
        large.setFontColor(GenoColor.getThemeColor());
        p1.add(new Text("尊敬的 ").addStyle(large));
        p1.add(new Text(index.getName()).addStyle(large).setBorderBottom(new SolidBorder(0.5f)));
        p1.add(new Text(" " + ("FEMALE".equals(index.getGender()) ? "女士" : "MALE".equals(index.getGender()) ? "先生" : "") + "：\n").addStyle(large));
        p1.add(new Text("您好！\n"));
        p1.add(new Text("\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0衷心感谢您对我们的信任，选择壹基因疾病遗传风险基因检测服务!\n" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0当您收到这份报告书的时候，我们已经根据您的需求，秉持科学、专业、严谨和保密的态度为您完成了本次基因检测，并根据您的个人特有基因检测结果进行了全面深入的分析。\n" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0现代医学研究表明，所有疾病（除外伤）的发生均与基因有关，而易感基因与疾病的发生有着非常密切的关系，患病是基因（内因）与外部环境（外因）共同作用的结果。我们只有了解自身的基因奥秘、预测出疾病的发生风险，才能更好的利用现代医学手段，做到早预防、早诊断、早治疗，实现“上医治未病”的目的。\n" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0本报告是通过对受检者提供的生物样本（一般为口腔黏膜细胞）进行基因检测，依据国际有关疾病易感基因的公开研究成果和数据，参考国际权威23andme，对检测结果进行分析，计算出受检者患某些疾病的风险指数。医学专家顾问团队依据患病风险等级给出针对性的饮食、运动、常规体检等方面的健康建议，旨在帮助受检者根据个人基因信息，科学合理地加强自身健康管理，预防疾病的发生。\n" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0希望本次检测能为您带来舒适满意的体验，针对本次检测，如果您有任何疑问需要解答，敬请拨打我们的健康热线400-163-5588。我们恭候您的来电，并保证给予及时、专业、贴心的回复。\n" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0壹基因衷心祝愿您身体健康、享受品质生活！"));
        div.add(p1);


        Painting painting = new Painting(pdf);
        painting.drawHello("image/纸质报告-03.png");
        painting.close();

        doc.add(div);
        return this;
    }

    @Override
    public ReportBuilder addExaminee() {
        ReportBean.IndexBean index = reportBean.getIndex();

        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        Div div1 = new Div();
        Image iconImage27 = new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/icon-27.png")));
        Paragraph p1 = new Paragraph();
        p1.add(iconImage27.addStyle(GenoStyle.getIconStyle()));
        p1.add(new Text("受检人信息").addStyle(GenoStyle.getTitleStyle()));
        div1.add(p1);
        doc.add(div1);

        Div div2 = new Div();
        div2.addStyle(GenoStyle.getDefaultTableOuter());
        Table table = new Table(3).useAllAvailableWidth();
        table.addCell(GenoComponent.getDefaultCell().add(GenoComponent.getSignParagraph("姓 名：" + index.getName())));
        table.addCell(GenoComponent.getDefaultCell().add(GenoComponent.getSignParagraph("性 别： " + ("FEMALE".equals(index.getGender()) ? "女" : "MALE".equals(index.getGender()) ? "男" : ""))));
        table.addCell(GenoComponent.getDefaultCell().add(GenoComponent.getSignParagraph("年 龄：" + (DateUtil.year(new Date()) - index.getBirthYear()))));
        table.startNewRow();
        table.addCell(GenoComponent.getDefaultCell().add(GenoComponent.getSignParagraph("样本编号：" + index.getSampleCode())));
        table.addCell(GenoComponent.getDefaultCell().add(GenoComponent.getSignParagraph("样本类型：基因组DNA")));
        table.startNewRow();
        table.addCell(GenoComponent.getDefaultCell().add(GenoComponent.getSignParagraph("样本检测结果：合格")));
        table.addCell(GenoComponent.getDefaultCell().add(GenoComponent.getSignParagraph("报告日期：" + (DateUtil.format(new Date(), "yyyy年M月d日")))));
        div2.add(table);
        doc.add(div2);
        return this;
    }


    @Override
    public GenoReportBuilder addDetectionContent() {
        ReportBean.IndexBean index = reportBean.getIndex();

        Div div1 = new Div();
        div1.setMargins(30, 0, 20, 0);
        Image iconImage27 = new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/icon-29.png")));
        Paragraph p1 = new Paragraph();
        p1.add(iconImage27.addStyle(GenoStyle.getIconStyle()));
        p1.add(new Text("检测内容").addStyle(GenoStyle.getTitleStyle()));
        div1.add(p1);

        Paragraph p2 = new Paragraph();
        p2.setFontSize(11);
        p2.add("检测套餐：" + index.getPackageName() + "\n");
        p2.add("检测方法：高通量基因分型技术\n");
        p2.add("检测形式：对受检者基因组中与各项检测项目密切相关的基因信息进行DNA分型检测。\n");
        div1.add(p2);

        Paragraph p3 = new Paragraph();
        p3.add(new Text("用户须知\n").addStyle(GenoStyle.getSecondTitleStyle()));
        p3.add(new Text("1、检测基因和位点的筛选均基于国际权威数据库（HGMD、OMIM等）和权威学术研究报道。\n" +
                "2、检测基因和位点以及用于解读报告的数据库，会随着基因库的增大和医学研究的更新而升级优化。\n" +
                "3、检测结果可用于指导生活习惯、健康管理和疾病预防⽅案的制定，不作为临床诊断和治疗的依据。\n" +
                "4、疾病的发生、发展与基因变异密切相关，是基因（内因）与外部环境（外因）共同作用的结果。\n" +
                "5、基因检测的目的不是去忽视低风险疾病，而是找出高风险疾病，并进行针对性的预防和健康管理，\n" +
                "以延缓或规避相应疾病的发生。"));
        Div div2 = new Div();
        div2.setMarginTop(30);
        Table signatureTable = new Table(2);
        signatureTable.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        signatureTable.setWidth(UnitValue.createPercentValue(50));
        signatureTable.addCell(GenoComponent.getSignCell().add(GenoComponent.getSignParagraph("检测人：舒平")));
        signatureTable.addCell(GenoComponent.getSignCell().add(GenoComponent.getSignParagraph("复核人：张萍")));
        signatureTable.startNewRow();
        signatureTable.addCell(GenoComponent.getSignCell().add(GenoComponent.getSignParagraph("日期：" + (DateUtil.format(new Date(), "yyyy年M月d日")))));
        signatureTable.addCell(GenoComponent.getSignCell().add(GenoComponent.getSignParagraph("日期：" + (DateUtil.format(new Date(), "yyyy年M月d日")))));
        div2.add(signatureTable);

        Image iconImage01 = new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/image-01.png")));
        iconImage01.setWidth(105);
        iconImage01.setHeight(105);
        iconImage01.setRelativePosition(295, 0, 60, 90);
        div2.add(iconImage01);
        doc.add(div1);
        doc.add(p3);
        doc.add(div2);

        return this;
    }

    @Override
    public GenoReportBuilder addDirectory() {
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        Div div1 = new Div();
        Paragraph p1 = new Paragraph();
        p1.add(new Text("目录").addStyle(GenoStyle.getTitleStyle()).setFontSize(32));

        Table tableCatalog = new Table(4).useAllAvailableWidth();
        tableCatalog.addCell(GenoComponent.getCatelogCell().add(new Paragraph("检测结果概况").addStyle(GenoStyle.getSecondTitleStyle())));
        tableCatalog.addCell(GenoComponent.getCatelogCell(2).add(GenoComponent.getCatelogDottedLine(1)));
        tableCatalog.addCell(GenoComponent.getCatelogCell().add(new Paragraph("8")));
        tableCatalog.startNewRow();
        // 需要注意 动态变化
        tableCatalog.addCell(GenoComponent.getCatelogCell().add(new Paragraph("需要注意").addStyle(GenoStyle.getSecondTitleStyle())));
        tableCatalog.startNewRow();

        tableCatalog.addCell(GenoComponent.getCatelogCell().add(new Paragraph("宫颈癌")));
        tableCatalog.addCell(GenoComponent.getCatelogCell().add(GenoComponent.getCatelogDottedLine(2)));
        tableCatalog.addCell(GenoComponent.getCatelogCell().add(new List().add(new ListItem("风险高").setListSymbol(new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/red-point.png"))).addStyle(GenoStyle.getDefaultPoint())))));
        tableCatalog.addCell(GenoComponent.getCatelogCell().add(new Paragraph("10")));
        tableCatalog.startNewRow();

        // 正常项目 动态变化
        tableCatalog.addCell(GenoComponent.getCatelogCell().add(new Paragraph("正常项目").addStyle(GenoStyle.getSecondTitleStyle())));
        tableCatalog.startNewRow();
        tableCatalog.addCell(GenoComponent.getCatelogCell().add(new Paragraph("常见肿瘤类").addStyle(GenoStyle.getSecondTitleStyle().setFontSize(13))));
        tableCatalog.startNewRow();

        tableCatalog.addCell(GenoComponent.getCatelogCell().add(new Paragraph("胃癌")));
        tableCatalog.addCell(GenoComponent.getCatelogCell().add(GenoComponent.getCatelogDottedLine(2)));
        tableCatalog.addCell(GenoComponent.getCatelogCell().add(new List().add(new ListItem("风险正常").setListSymbol(new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/blue-point.png"))).addStyle(GenoStyle.getDefaultPoint())))));
        tableCatalog.addCell(GenoComponent.getCatelogCell().add(new Paragraph("23")));
        tableCatalog.startNewRow();

        tableCatalog.addCell(GenoComponent.getCatelogCell().add(new Paragraph("结束语").addStyle(GenoStyle.getSecondTitleStyle())));

        div1.add(p1);
        div1.add(tableCatalog);
        doc.add(div1);
        return this;
    }

    @Override
    public GenoReportBuilder addResultSummary() {
        java.util.List<ReportBean.CategoriesBean> categories = reportBean.getCategories();
        ReportBean.IndexBean index = reportBean.getIndex();

        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        Paragraph header = GenoComponent.getHeaderLineText("检测结果概况");

        Paragraph p1 = new Paragraph();
        p1.add(new Text("检测结果概况\n").addStyle(GenoStyle.getTitleStyle()));
        p1.add(new Text("本次检测包括"));
        int size = categories.size();
        for (int i = 0; i < size; i++) {
            ReportBean.CategoriesBean category = categories.get(i);
            p1.add(new Text(category.getName()).addStyle(GenoStyle.getThirdTitleStyle()));
            if (i != size - 1) {
                p1.add(new Text("、").addStyle(GenoStyle.getThirdTitleStyle()));
            }

        }
        p1.add(new Text(size + "部分内容\n"));
        p1.add(new Text("共计"));
        p1.add(new Text(index.getUnlockedItems() + "").addStyle(GenoStyle.getThirdTitleStyle()));
        p1.add(new Text("项检测项目"));

        Table t1 = new Table(2).useAllAvailableWidth();
        t1.setMargins(30, 0, 20, 0);
        Image goodTipImage = new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/goodtip.png")));
        t1.addCell(GenoComponent.getDefaultCell(2, 1).setWidth(70).setPaddingBottom(30).add(goodTipImage.addStyle(GenoStyle.getLargeIconStyle())));
        t1.addCell(GenoComponent.getDefaultCell().add(new Paragraph("优势标签").addStyle(GenoStyle.getThirdTitleStyle())));
        // 优势标签
        java.util.List<ReportBean.IndexBean.TagBean> goodTags = index.getGoodTags();
        int goodSize = goodTags.size();
        if (goodSize <= 0) {
            t1.addCell(GenoComponent.getDefaultCell().add(new Paragraph("暂无优势项目")));
        } else {
            Div tabDiv = new Div();
            Paragraph element = new Paragraph();
            for (int i = 0; i < goodSize; i++) {
                ReportBean.IndexBean.TagBean tagBean = goodTags.get(i);
                Text text = new Text(tagBean.getLabel() + ((i + 1) % 3 == 0 ? "\n" : ""));
                Style style = new Style();
                style.setPaddings(3, 10, 3, 10);
                style.setBackgroundColor(GenoColor.getThemeColor());
                style.setBorderRadius(new BorderRadius(10));
                style.setFontColor(ColorConstants.WHITE);
                style.setMargins(0, 3, 0, 3);
                text.addStyle(style);
                element.add(text);
            }
            t1.addCell(GenoComponent.getDefaultCell().add(tabDiv));
        }
        t1.startNewRow();

        // 需要注意
        Image badTipImage = new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/badtip.png")));
        t1.addCell(GenoComponent.getDefaultCell(2, 1).setWidth(70).setPaddingRight(20).add(badTipImage.addStyle(GenoStyle.getLargeIconStyle())));
        t1.addCell(GenoComponent.getDefaultCell().add(new Paragraph("需要注意").addStyle(GenoStyle.getThreeTitleOrangeStyle())));


        java.util.List<ReportBean.IndexBean.TagBean> badTags = index.getBadTags();
        int badSize = badTags.size();
        if (badSize == 0) {
            t1.addCell(GenoComponent.getDefaultCell().add(new Paragraph("暂无优势项目")));
        } else {
            Div tabDiv = new Div();
            Paragraph element = new Paragraph();
            for (int i = 0; i < badSize; i++) {
                ReportBean.IndexBean.TagBean tagBean = badTags.get(i);
                Text text = new Text(tagBean.getLabel() + ((i + 1) % 3 == 0 ? "\n" : ""));
                Style style = new Style();
                style.setPaddings(3, 10, 3, 10);
                style.setBackgroundColor(GenoColor.getOrangeColor());
                style.setBorderRadius(new BorderRadius(10));
                style.setFontColor(ColorConstants.WHITE);
                style.setMargins(0, 3, 0, 3);
                text.addStyle(style);
                element.add(text);
            }
            tabDiv.add(element);
            t1.addCell(GenoComponent.getDefaultCell().add(tabDiv));
        }


        Paragraph p2 = new Paragraph();
        p2.setFixedPosition(60, 90, UnitValue.createPercentValue(110));
        p2.add(GenoComponent.getSecondTitle("温馨提示\n"));
        p2.add(new Text("1、本检测报告的每项内容主要由检测结果、基因位点信息、简介、健康建议等组成。\n" +
                "2、我们用基因风险指数来解释和划分您的疾病遗传风险等级,共分为五级,五级及分级" +
                "标准为:低风险<0.5、0.5≤稍低风险<0.8、0.8≤中等或正常水平风险<1.2、1.2≤稍高风险<1.8、高风险≥1.8。\n" +
                "3、基因位点信息由四列内容组成,分别为本项目所检测的位点名称、参考基因型(参考型)、检测出" +
                "的基因型(检出型)及该基因型的作用。\n" +
                "4、因篇幅限制,本页仅展示了部分\"需要注意\"的项目,请在目录或详细检测结果查看全部该类项目。"));

        doc.add(header);
        doc.add(p1);
        doc.add(t1);
        doc.add(p2);

        Painting painting = new Painting(pdf);
        painting.drawHeader();
        painting.close();

        // 加个详细检测结果页
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        Image image88 = new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/成人纸质报告8.8-10.png")));
        doc.add(image88);
        return this;
    }

    @Override
    public GenoReportBuilder addContext() {
        java.util.List<ReportBean.ItemsBean> gaoLevel = new ArrayList<>();
        java.util.List<ReportBean.ItemsBean> normalLecel = new ArrayList<>();
        java.util.List<ReportBean.CategoriesBean> categories = reportBean.getCategories();
        for (ReportBean.CategoriesBean category : categories) {
            java.util.List<ReportBean.CategoriesBean.ItemsBean> items = category.getItems();
            for (ReportBean.CategoriesBean.ItemsBean item : items) {
                if (!item.getLocked()) {
                    ReportBean.ItemsBean itemsBean = reportBean.getItems().get(item.getCode());
                    if (Integer.parseInt(item.getLevel()) >= 3) {
                        gaoLevel.add(itemsBean);
                    } else if (Integer.parseInt(item.getLevel()) < 3) {
                        normalLecel.add(itemsBean);
                    }
                }
            }
        }
        Painting painting = new Painting(pdf);

        // 高危项目
        for (ReportBean.ItemsBean itemsBean : gaoLevel) {
            float score = itemsBean.getScore();
            String title = itemsBean.getName();
            java.util.List<ReportBean.ItemsBean.GeneDescBean> geneDesc = itemsBean.getGeneDesc();
            java.util.List<ReportBean.ItemsBean.ContentsBean> contents = this.handlerContents(itemsBean);
            java.util.List<ReportBean.ItemsBean.LiteraturesBean> literatures = itemsBean.getLiteratures();

            HeaderTextEvent headerTextEvent = new HeaderTextEvent(title);
            pdf.addEventHandler(PdfDocumentEvent.START_PAGE, headerTextEvent);

            doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

            Paragraph p1 = new Paragraph();
            p1.add(new Text(itemsBean.getName() + "\n").addStyle(GenoStyle.getTitleStyle()));
            p1.add(GenoComponent.getSecondTitle("检测结果"));
            doc.add(p1);

            String categoryCode = itemsBean.getCategoryCode();
            boolean gaoLevelFlag = "cjzl".equals(categoryCode) || "xhxt".equals(categoryCode) || "cs".equals(categoryCode) || "jsxl".equals(categoryCode) || "ln".equals(categoryCode) || "nfm".equals(categoryCode) || "xnxg".equals(categoryCode) || "zsmy".equals(categoryCode);
            if (gaoLevelFlag) {
                // 头条，进度条
                painting.drawSegment(score);

                // 等级
                Div progressBlock = new Div();
                Paragraph level = new Paragraph();
                level.setWidth(200);
                Style style = new Style();
                style.setFontSize(11);
                style.setMargins(2, -4, 2, 0);
                style.setPaddings(3, 12, 3, 12);
                Style attentionStyle = new Style();
                attentionStyle.setBorderRadius(new BorderRadius(5));
                attentionStyle.setFontColor(ColorConstants.WHITE);

                Text level1 = new Text("低").addStyle(style);
                Text level2 = new Text("稍低").addStyle(style);
                Text level3 = new Text("正常").addStyle(style);
                Text level4 = new Text("稍高").addStyle(style);
                Text level5 = new Text("高").addStyle(style);
                String[] segmenets = Constant.SEGMENETS;
                Color color = null;
                if (score >= Float.parseFloat(segmenets[0]) && score < Float.parseFloat(segmenets[1])) {
                    color = GenoColor.getLightBlueColor();
                    attentionStyle.setBackgroundColor(color);
                    level1.addStyle(attentionStyle);
                } else if (score >= Float.parseFloat(segmenets[1]) && score < Float.parseFloat(segmenets[2])) {
                    color = GenoColor.getLightBlueColor();
                    attentionStyle.setBackgroundColor(color);
                    level2.addStyle(attentionStyle);
                } else if (score >= Float.parseFloat(segmenets[2]) && score < Float.parseFloat(segmenets[3])) {
                    color = GenoColor.getThemeColor();
                    attentionStyle.setBackgroundColor(color);
                    level3.addStyle(attentionStyle);
                } else if (score >= Float.parseFloat(segmenets[3]) && score < Float.parseFloat(segmenets[4])) {
                    color = GenoColor.getOrangeColor();
                    attentionStyle.setBackgroundColor(color);
                    level4.addStyle(attentionStyle);
                } else if (score >= Float.parseFloat(segmenets[4]) && score <= Float.parseFloat(segmenets[5])) {
                    color = GenoColor.getRedColor();
                    attentionStyle.setBackgroundColor(color);
                    level5.addStyle(attentionStyle);
                }
                level.add(level1);
                level.add(level2);
                level.add(level3);
                level.add(level4);
                level.add(level5);
                level.setHorizontalAlignment(HorizontalAlignment.CENTER);
                progressBlock.add(level);
                doc.add(progressBlock);

                // 正文
                int index = 0;
                Paragraph p2 = new Paragraph();
                p2.setMarginTop(60);
                p2.add(new Tab());
                p2.addTabStops(new TabStop(20, TabAlignment.LEFT));
                p2.add("您的基因风险指数为");
                p2.add(new Text(score + "").setFontColor(color));
                switch (index) {
                    case 0:
                    case 1:
                        p2.add("。在相同外界条件下，与大多数人相比，您具有较好的基因优势。但也不要忽视了预防，疾病是由基因和外界环境共同作用而导致的，因此仍建议您注意规避外界风险因素，保持良好的生活习惯，加强锻炼，定期进行体检和相关筛查。");
                        break;
                    case 2:
                        p2.add("。在相同外界条件下，您与大多数人的患病风险相同。基因风险相同情况下，最终患病与否，与外界环境、生活习惯和医疗条件的差异密切相关。因此，仍建议您重视预防" + itemsBean.getName() + "，注意规避外界风险因素，保持良好的生活习惯，加强锻炼，定期进行体检和相关筛查。");
                    case 3:
                        p2.add("。在相同外界条件下，您患" + itemsBean.getName() + "的风险比正常人群稍高。建议您尽可能规避相关高危因素、调整生活习惯，改变不利的生活方式，定期进行体检和相关筛查，认真对待相关急慢性疾病（如发生）的治疗和医疗干预。");
                    case 4:
                        p2.add("。在相同外界条件下，您患" + itemsBean.getName() + "的风险高于正常人群。建议您尽可能规避" + itemsBean.getName() + "相关高危因素、调整生活习惯，改变不利的生活方式，定期进行体检和相关筛查，认真对待相关急慢性疾病（如发生）的治疗和医疗干预。");
                    default:
                        break;
                }
                doc.add(p2);
            }

            // TODO 遗传病不知道样式
            if ("ycb".equals(categoryCode)) {
                if ("未检出".equals(itemsBean.getLabel())) {

                }
            }
            // 维生素矿物质
            boolean wssFlag = "wss".equals(categoryCode) || "kwz".equals(categoryCode);
            if (wssFlag) {
                painting.drawWss(Integer.parseInt(itemsBean.getLevel()), itemsBean);
            }

            // 个性特质 天赋潜能 美容护肤 纤体瘦身
            boolean personFlag = "gxtz".equals(categoryCode) || "jfss".equals(categoryCode) || "mrhf".equals(categoryCode) || "jfss".equals(categoryCode) || "categoryCode".equals(itemsBean.getCode());
            if (personFlag) {
                this.addPersonalityTraits(itemsBean, categoryCode);
            }

            // 基因位点信息
            Paragraph p2 = new Paragraph("基因位点信息").addStyle(GenoStyle.getSecondTitleStyle());
            if (wssFlag) {
                p2.setMarginTop(110);
            }
            doc.add(p2);
            Table geneLocusTable = new Table(4).useAllAvailableWidth();
            geneLocusTable.addCell(GenoComponent.getTableCell().add(new Paragraph("基因位点")).addStyle(GenoStyle.getTableHeader()));
            geneLocusTable.addCell(GenoComponent.getTableCell().add(new Paragraph("参考型")).addStyle(GenoStyle.getTableHeader()));
            geneLocusTable.addCell(GenoComponent.getTableCell().add(new Paragraph("检出型")).addStyle(GenoStyle.getTableHeader()));
            geneLocusTable.addCell(GenoComponent.getTableCell().add(new Paragraph("基因型解释")).addStyle(GenoStyle.getTableHeader()));
            geneLocusTable.startNewRow();
            for (ReportBean.ItemsBean.GeneDescBean geneDescBean : geneDesc) {
                geneLocusTable.addCell(GenoComponent.getTableCell().setPadding(0).add(new Paragraph(geneDescBean.getOg_id())));
                geneLocusTable.addCell(GenoComponent.getTableCell().setPadding(0).add(new Paragraph(geneDescBean.getRef_genotype())));
                geneLocusTable.addCell(GenoComponent.getTableCell().setPadding(0).add(new Paragraph(geneDescBean.getGenotype())));
                geneLocusTable.addCell(GenoComponent.getTableCell().setPadding(0).add(new Paragraph(geneDescBean.getLabel())));
                geneLocusTable.startNewRow();
            }
            doc.add(geneLocusTable);

            // 简介，症状，健康建议，基因解读
            long count = contents.stream().filter(content -> "健康建议".equals(content.getLabel())).count();
            long count2 = count - 1;
            for (ReportBean.ItemsBean.ContentsBean content : contents) {
                if ("健康建议".equals(content.getLabel())) {
                    count--;
                    if (count < count2) {
                        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                    }
                }
                doc.add(GenoComponent.getTitleParagraph(GenoComponent.getSecondTitle(content.getLabel())));
                String value = content.getValue();
                java.util.List<IElement> iElements = getFixContent(value);
                for (IElement iElement : iElements) {
                    Style style = new Style();
                    style.setFontSize(10);
                    style.setCharacterSpacing(0.8f);
                    if (iElement instanceof Div) {
                        Div div = (Div) iElement;
                        java.util.List<IElement> children = div.getChildren();
                        for (IElement child : children) {
                            if (child instanceof Paragraph) {
                                Paragraph element = (Paragraph) child;
                                element.addStyle(style);
                                element.setFixedLeading(15);
                                element.setMultipliedLeading(2);
                            }
                        }
                        doc.add(div);
                    } else if (iElement instanceof Paragraph) {
                        Paragraph element = (Paragraph) iElement;
                        element.setFixedLeading(15);
                        element.setMultipliedLeading(2);
                        doc.add(element.addStyle(style));
                    }
                }

            }

            // 文献
            int number = 1;
            Div literatureDiv = new Div();
            Paragraph titleParagraph = GenoComponent.getTitleParagraph(new Text("参考文献（部分）").addStyle(GenoStyle.getSecondTitleStyle()));
            literatureDiv.add(titleParagraph);
            literatureDiv.setKeepTogether(true);
            for (ReportBean.ItemsBean.LiteraturesBean literature : literatures) {
                Paragraph segment = new Paragraph();
                segment.setFontSize(9f).setFontColor(new DeviceRgb(85, 85, 85));
                segment.add(new Text("[" + (number++) + "]"));
                segment.add(new Text(literature.getAuthor() + ". "));
                segment.add(new Text(literature.getTitle() + ". "));
                segment.add(new Text(literature.getJournal() + ". "));
                segment.add(new Text(literature.getSerialNumber()));
                literatureDiv.add(segment);
            }
            doc.add(literatureDiv);

            // 移除监听器
            pdf.removeEventHandler(PdfDocumentEvent.START_PAGE, headerTextEvent);
        }

        // 正常项目
        for (ReportBean.ItemsBean itemsBean : normalLecel) {

        }

        painting.close();
        return this;
    }

    private java.util.List<IElement> getFixContent(String content) {
        if (content.startsWith("<div>")) {
            content = content.replaceAll("<div>", "<div style='line-height:20pt'>");
        } else {
            content = "<div style='line-height:20pt'>" + content + "</div>";
        }
        return HtmlConverter.convertToElements(content, proper);
    }

    private java.util.List<ReportBean.ItemsBean.ContentsBean> handlerContents(ReportBean.ItemsBean itemsBean) {
        java.util.List<ReportBean.ItemsBean.ContentsBean> contents = itemsBean.getContents();
        int size = contents.size();
        if (size > 6) {
            java.util.List<ReportBean.ItemsBean.ContentsBean> result = new ArrayList<>();
            java.util.List<ReportBean.ItemsBean.ContentsBean> contentsBeans = contents.subList(2, 6);
            result.add(contents.get(0));
            result.add(contents.get(1));
            for (int i = 6; i < size; i++) {
                ReportBean.ItemsBean.ContentsBean contentsBean = contents.get(i);
                contentsBean.setLabel(contentsBean.getLabel().split("-")[0]);
                result.add(contentsBean);
            }
            itemsBean.setContents(result);
            result.add(contentsBeans.get(contentsBeans.size() - 1));
        }
        return itemsBean.getContents();
    }

    private void addPersonalityTraits(ReportBean.ItemsBean itemsBean, String categoryCode) {
        Color[] colors = new Color[]{new DeviceRgb(112, 223, 191), new DeviceRgb(30, 191, 190), new DeviceRgb(37, 98, 206), new DeviceRgb(248, 182, 45), new DeviceRgb(242, 107, 111)};
        URL resource = Painting.class.getClassLoader().getResource("image/individual/" + (itemsBean.getIndex() + 1) + ".png");
        Image personImage = new Image(ImageDataFactory.create(resource));
        personImage.scale(0.25f, 0.25f);
        personImage.setHorizontalAlignment(HorizontalAlignment.CENTER);
        doc.add(personImage);
        boolean which = !"jfss".equals(categoryCode) && "gxtz".equals(categoryCode);
        if (which) {
            Paragraph element = new Paragraph("您的" + itemsBean.getName());
            element.setTextAlignment(TextAlignment.CENTER);
            element.add(new Text(itemsBean.getLabel()).setFontColor(colors[itemsBean.getIndex()]));
            doc.add(element);
        }
        which = "gxtz".equals(categoryCode) || "jfss".equals(categoryCode);
        if (which) {
            Paragraph element = new Paragraph();
            element.setTextAlignment(TextAlignment.CENTER);
            element.add(new Text(itemsBean.getDescription()).setFontColor(colors[itemsBean.getIndex()]));
            doc.add(element);
        }
    }

    @Override
    public GenoReportBuilder addThanks() {
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        Paragraph p1 = new Paragraph();
        p1.setHorizontalAlignment(HorizontalAlignment.CENTER);
        p1.setMaxWidth(UnitValue.createPercentValue(75));
        p1.setMarginTop(200f);
        p1.setCharacterSpacing(0.4f);
        p1.add(new Text("\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0再次感谢您选用壹基因提供的基因检测服务。\n" +
                "\n" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0疾病的发生受到先天和后天两个因素的共同影响，某疾病先天遗传风险低并不代表患病概率是零，如果后天生活习惯很差，生存环境不好，都会提升疾病的发生风险，促进疾病的最终发生；做基因检测的目的，不是去忽视低风险的疾病，而是要找出高风险疾病进行重点预防，采取措施进行针对性干预，以便阻断或者延缓疾病的发生。\n" +
                "\n" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0您可以通过我们提供的健康建议开展健康管理，对风险较高项目积极预防，如改变饮食习惯，定期进行针对性的临床体检等。若您携带某种疾病易感基因型，该基因型很可能也存在于您亲属的基因中并遗传给他们的后代。因此，壹基因建议您的亲属也针对这些高风险项目进行检测，以了解自身健康风险，及早采取干预措施，拥有健康品质生活。\n" +
                "\n" +
                "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0如果您对我们检测服务和体验有任何意见或建议，敬请拨打我们的健康热线400-163-5588，或者手机扫描下部二维码，联系您的专属健康顾问。"));
        doc.add(p1);
        URL resource = Painting.class.getClassLoader().getResource("image/结束语.png");
        Image backImage = new Image(ImageDataFactory.create(resource));
        int pageNum = pdf.getNumberOfPages();
        backImage.setFixedPosition(pageNum, 0, 0, UnitValue.createPercentValue(125));
        backImage.scale(1, 1.05f);
        doc.add(backImage);

        return this;
    }

    @Override
    public GenoReportBuilder addBackCover() {
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
        doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

        Image backCoverImage = new Image(ImageDataFactory.create(GenoReportBuilder.class.getClassLoader().getResource("image/封底-02.png")));
        backCoverImage.setWidth(UnitValue.createPercentValue(100));
        backCoverImage.scale(1.3f, 1.3f);
        backCoverImage.setMarginLeft(-70);
        backCoverImage.setMarginTop(-80);
        doc.add(backCoverImage);
        return this;
    }

}