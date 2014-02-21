package com.litetech.omt.ui.comp;

import static com.litetech.omt.ui.model.ProductUnitPriceTableModel.UNIT_PRICE_HEADER;
import static com.litetech.omt.ui.model.SearchProductResultModel.header;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.litetech.omt.client.ServiceClient;
import com.litetech.omt.constant.ProductStatusEnum;
import com.litetech.omt.constant.ProductUnitLevelEnum;
import com.litetech.omt.ui.comp.action.ProductTableAction;
import com.litetech.omt.ui.comp.editor.ConfigureProductEditor;
import com.litetech.omt.ui.comp.editor.ProductCellAddRemoveEditor;
import com.litetech.omt.ui.comp.editor.ProductCellAddRemoveEditor.ProductCellAddRemoveEditorEnum;
import com.litetech.omt.ui.comp.listener.TableCellListener;
import com.litetech.omt.ui.comp.renderer.EditProductRenderer;
import com.litetech.omt.ui.comp.renderer.LiteComboModelCellRenderer;
import com.litetech.omt.ui.comp.renderer.LiteComboModelRenderer;
import com.litetech.omt.ui.comp.renderer.ProductUnitPriceAddRenderer;
import com.litetech.omt.ui.comp.renderer.ProductUnitPriceCloseRenderer;
import com.litetech.omt.ui.comp.validator.ProductValidator;
import com.litetech.omt.ui.model.LiteComboModel;
import com.litetech.omt.ui.model.ProductFormModel;
import com.litetech.omt.ui.model.ProductUnitPriceTableModel;
import com.litetech.omt.ui.model.SearchProductResultModel;
import com.litetech.omt.ui.model.ProductFormModel.ProductDetailModel;
import com.litetech.omt.ui.model.ds.ProductFormDSModel;
import com.litetech.omt.vo.ProductUnitVO;
import com.litetech.omt.vo.ProductVO;
import com.litetech.omt.vo.SearchProductRequestVO;
import com.litetech.omt.vo.UnitVO;

public class ProductForm extends JPanel implements PanelTabComp{

	private JPanel rootPanel;
	private JPanel resultPanel;
	private JPanel productPanel;
	
	private ProductFormModel formModel;
	private ProductFormDSModel formDSModel;

	private JTable productTable;
	private JTable unitPriceTable;
	//search fields
	private JTextField SRCH_FIELD_PROD_ID;
	private JTextField SRCH_FIELD_PROD_NAME;
	private JComboBox  SRCH_FIELD_PROD_STATUS;
	//private JComboBox  SRCH_FIELD_PROD_UNIT;
	private JTextField SRCH_FIELD_PROD_PRICE;
	private JTextArea FIELD_PROD_DESC;
	
	private JTextField FIELD_PROD_ID;
	private JTextField FIELD_PROD_NAME;
	private JTextField FIELD_PROD_CODE;
	private JComboBox FIELD_PROD_STATUS;
	private JTextField FIELD_PROD_QTY;
	private JComboBox FIELD_PROD_QTY_UNIT;
	private ProductValidator productValidator;
	
	private JPanel myPanel;
	@Override
	public void setMyPanelTab(JPanel myPanel) {
		this.myPanel = myPanel;	
	}


	@Override
	public JPanel getMyPanelTab() {
		return myPanel;
	}
	
	public ProductForm(ProductFormModel formModel, ProductFormDSModel formDSModel){
		
		this.formModel = formModel;
		this.formDSModel = formDSModel;
		
		this.setBackground(Color.WHITE);
		rootPanel = new JPanel();
		rootPanel.setBackground(Color.WHITE);
		rootPanel.setPreferredSize(AppContext.getInstance().getBaseFormPanelDimension());
		
		productValidator = new ProductValidator();
		rootPanel.add(constructSearchPanel());
		rootPanel.add(constructResultPanel());
		//rootPanel.add(constructProductPanel());
		add(rootPanel);
		
	}
	
	
	
	private JPanel constructSearchPanel(){
		JPanel searchPanel = new JPanel();
		searchPanel.setBackground(Color.WHITE);
		int width = AppContext.getInstance().getWorkAreaWidth();
		searchPanel.setPreferredSize(new Dimension(width, 30));
		
		int intermediateSpace = (width / (9 * 10)) * 4;
		int interColSpace = 10;
		
		
		Box firstRow = Box.createHorizontalBox();
		
		JLabel productIdLabel = new JLabel("Product ID");
		SRCH_FIELD_PROD_ID = new JTextField("", 10);
				
		firstRow.add(productIdLabel);
		firstRow.add(Box.createHorizontalStrut(interColSpace));
		firstRow.add(SRCH_FIELD_PROD_ID);
		firstRow.add(Box.createHorizontalStrut(intermediateSpace));
		
		JLabel productNameLabel = new JLabel("Product Name");
		SRCH_FIELD_PROD_NAME = new JTextField("", 12);
		
		firstRow.add(productNameLabel);
		firstRow.add(Box.createHorizontalStrut(interColSpace));
		firstRow.add(SRCH_FIELD_PROD_NAME);
		firstRow.add(Box.createHorizontalStrut(intermediateSpace));
		
		JLabel statusLabel = new JLabel("Status");
		
		Vector<LiteComboModel> productStatusList = new Vector<LiteComboModel>();
		
		ProductStatusEnum[] statuses = ProductStatusEnum.values();
		for(ProductStatusEnum status : statuses){
			productStatusList.add(new LiteComboModel(status.getId(), status.name()));
		}
		
		SRCH_FIELD_PROD_STATUS = new JComboBox(productStatusList);
		SRCH_FIELD_PROD_STATUS.setSelectedItem(new LiteComboModel(1));
		SRCH_FIELD_PROD_STATUS.setRenderer(new LiteComboModelRenderer());
		
		firstRow.add(statusLabel);
		firstRow.add(Box.createHorizontalStrut(interColSpace));
		firstRow.add(SRCH_FIELD_PROD_STATUS);
		firstRow.add(Box.createHorizontalStrut(intermediateSpace));
		
		
		/*JLabel primaryUnitLabel = new JLabel("Primary Unit");
		
		Vector<LiteComboModel> unitComboModels = new Vector<LiteComboModel>();
		List<UnitVO> unitVOs = formDSModel.getUnitVOs();
		for(UnitVO unitVO :  unitVOs){
			unitComboModels.add(new LiteComboModel(unitVO.getId(), unitVO.getName()));
		}
		
		SRCH_FIELD_PROD_UNIT = new JComboBox(unitComboModels);
		SRCH_FIELD_PROD_UNIT.setRenderer(new LiteComboModelRenderer());
		
		firstRow.add(primaryUnitLabel);
		firstRow.add(Box.createHorizontalStrut(interColSpace));
		firstRow.add(SRCH_FIELD_PROD_UNIT);
		firstRow.add(Box.createHorizontalStrut(intermediateSpace));*/
		
		JLabel productPriceLabel = new JLabel("Price");
		JLabel greaterThanLabel = new JLabel(" > ");
		SRCH_FIELD_PROD_PRICE = new JTextField("", 5);
		
		firstRow.add(productPriceLabel);
		firstRow.add(Box.createHorizontalStrut(interColSpace));
		firstRow.add(greaterThanLabel);
		firstRow.add(Box.createHorizontalStrut(20));
		firstRow.add(SRCH_FIELD_PROD_PRICE);
		
		firstRow.add(Box.createHorizontalStrut(intermediateSpace));
		
		JButton search = new JButton("Search");
		search.addActionListener(new ProductSearchListener());
		
		firstRow.add(search);
		firstRow.add(Box.createHorizontalStrut(intermediateSpace));
		
		searchPanel.add(firstRow);
		
		return searchPanel;
	}
	

	private SearchProductRequestVO constructSearchRequest(){
		SearchProductRequestVO searchRequestVO = new SearchProductRequestVO();
		String prodIdStr = SRCH_FIELD_PROD_ID.getText();
		if(isNotEmpty(prodIdStr)){
			searchRequestVO.setProductId(new Long(prodIdStr.trim()));
		}
		
		String productNameStr = SRCH_FIELD_PROD_NAME.getText();
		if(isNotEmpty(productNameStr)){
			searchRequestVO.setProductNameLike(productNameStr.trim());
		}
		
		int productStatus = (int)((LiteComboModel)SRCH_FIELD_PROD_STATUS.getSelectedItem()).getId();
		searchRequestVO.setStatus(ProductStatusEnum.getById(productStatus));
		
		
		/*long primaryUnitId = ((LiteComboModel)SRCH_FIELD_PROD_UNIT.getSelectedItem()).getId();
		if(primaryUnitId != -1){
			searchRequestVO.setPrimaryUnitId(primaryUnitId);
		}*/
		
		String priceStr = SRCH_FIELD_PROD_PRICE.getText();
		if(isNotEmpty(priceStr)){
			searchRequestVO.setPriceGrTh(new Double(priceStr.trim()));
		}
		return searchRequestVO;
	}
	
	
	private boolean isNotEmpty(String str){
		boolean isEmpty = true;
		if(str != null && str.trim().equals("")){
			isEmpty = false;
		}
		return isEmpty;
	}
	
	public class ProductSearchListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SearchProductRequestVO searchRequestVO = constructSearchRequest();
			List<ProductVO> productVOs = ServiceClient.getInstance().searchProducts(searchRequestVO);
			populateResultPanel(productVOs);
		}
		
	}
	
	
	private void populateResultPanel(List<ProductVO> productVOs){
		formModel.setProductResultVOs(productVOs);
		rootPanel.add(constructResultPanel());
		rootPanel.add(constructProductPanel());
		ComponentRegistry.instance().repaintComponents();		
	}
	
	
	private JPanel constructResultPanel(){
		//remove existing panel
		if(resultPanel != null){
			rootPanel.remove(resultPanel);
		}
		
		resultPanel = new JPanel();
		//resultPanel.setBackground(Color.GREEN);
		int tableWidth = AppContext.getInstance().getWorkAreaWidth();
		resultPanel.setPreferredSize(new Dimension(tableWidth, 270));
		
		//Object[][] rows = {{10001l, "Test Product", "Box", 50.00, "Active", "24-02-2012", ""}};
		Object[][] rows = formModel.getResultRows();
		
		productTable = new JTable(new SearchProductResultModel(rows).getDefaultTableModel());
		productTable.setRowHeight(20);
		productTable.setBorder(LineBorder.createBlackLineBorder());
		productTable.setRowSelectionAllowed(false);
		productTable.setPreferredScrollableViewportSize(new Dimension(tableWidth-20, 210));
		productTable.setFillsViewportHeight(true);
		
		productTable.getColumn(header[0]).setPreferredWidth(100);
		productTable.getColumn(header[1]).setPreferredWidth(100);
		productTable.getColumn(header[2]).setPreferredWidth(100);
		productTable.getColumn(header[3]).setPreferredWidth(100);
		productTable.getColumn(header[4]).setPreferredWidth(100);
		productTable.getColumn(header[5]).setPreferredWidth(100);
		productTable.getColumn(header[6]).setPreferredWidth(100);
		//productTable.getColumn(header[6]).setMinWidth(75);
		//productTable.getColumn(header[6]).setMaxWidth(75);
		
		productTable.getColumn(header[7]).setPreferredWidth(20);
		productTable.getColumn(header[7]).setMinWidth(20);
		productTable.getColumn(header[7]).setMaxWidth(20);
		productTable.getColumn(header[7]).setCellRenderer(new EditProductRenderer());
		productTable.getColumn(header[7]).setCellEditor(new ConfigureProductEditor(new JCheckBox(), this));
		
		JScrollPane scrollPane = new JScrollPane(productTable);
		resultPanel.add(scrollPane);
		
		Box bottomRow = Box.createHorizontalBox();
		bottomRow.add(Box.createHorizontalStrut(tableWidth-150));
		
		JButton addNewProduct = new JButton("Add New Product");
		addNewProduct.addActionListener(new CreateProductListener());
		bottomRow.add(addNewProduct);
		resultPanel.add(bottomRow);
		return resultPanel;
	}

	
	public class CreateProductListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//formModel.setProductDetail(new ProductVO());
			populateProductPanel(new ProductVO());
		}
		
	}
	
	
	private ProductVO constructFormData(){
		ProductVO productVO = new ProductVO();
		
		long prodId = Long.valueOf(FIELD_PROD_ID.getText());
		String prodName = FIELD_PROD_NAME.getText().trim();
		String prodCode = FIELD_PROD_CODE.getText().trim();
		String prodDesc = FIELD_PROD_DESC.getText();
		int prodStatusId = (int)((LiteComboModel)FIELD_PROD_STATUS.getSelectedItem()).getId();
		double qty = Double.valueOf(FIELD_PROD_QTY.getText().trim());
		long measurableUnit = ((LiteComboModel)FIELD_PROD_QTY_UNIT.getSelectedItem()).getId(); 
		
		productVO.setProductId(prodId);
		productVO.setProductName(prodName);
		productVO.setProductCode(prodCode);
		productVO.setStatus(ProductStatusEnum.getById(prodStatusId));
		productVO.setMeasurableUnitVO(new UnitVO(measurableUnit));
		productVO.setQuantityAvl(qty);
		productVO.setProductDesc(prodDesc);
		populateUnitPriceData(productVO);
		
		return productVO;
	}
	
	
	
	private void populateUnitPriceData(ProductVO productVO){
		DefaultTableModel model = (DefaultTableModel)unitPriceTable.getModel();
		int rows = model.getRowCount();
		
		//set price
		Object priceObj = model.getValueAt(0, 4);
		Double price = new Double(priceObj.toString());
		productVO.setPrice(price);
		
		List<ProductUnitVO> productUnitVOs = new ArrayList<ProductUnitVO>();
		for(int row = 0; row < rows; row++){
			ProductUnitVO productUnitVO = new ProductUnitVO();
			productUnitVO.setProductId(productVO.getProductId());
			
			for(int col = 0; col < 7; col++){
				switch(col){
					case 0:
						Long productUnitId = new Long(model.getValueAt(row, col).toString());
						productUnitVO.setId(productUnitId);
						break;
					case 1:
						ProductUnitLevelEnum level = ProductUnitLevelEnum.valueOf(model.getValueAt(row, col).toString());
						productUnitVO.setLevel(level);
						break;
					case 2:	
						Long unitId = ((LiteComboModel)model.getValueAt(row, col)).getId();
						String unitName = ((LiteComboModel)model.getValueAt(row, col)).getName();
						productUnitVO.setUnitVO(new UnitVO(unitId, unitName));
						break;
						
					case 3:	
						Double priceRatio = new Double(model.getValueAt(row, col).toString());
						productUnitVO.setPriceRatio(priceRatio);
						break;	
											
					case 5:
						Double quanityRatio = new Double(model.getValueAt(row, col).toString());
						productUnitVO.setQuanityRatio(quanityRatio);
						break;
				}
			}
			
			productUnitVOs.add(productUnitVO);
		}
		
		productVO.setProductUnitVOs(productUnitVOs);
	}
	
	public class SaveProductListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			ProductVO productVO = constructFormData();
			if(productValidator.validateProduct(productVO)){
				ServiceClient.getInstance().saveProduct(productVO);
				resetFieldValues(productVO);
				JOptionPane.showMessageDialog(null, "Product Saved "+ productVO.getProductId());
			}
		}
			
	}
	
	private void resetFieldValues(ProductVO productVO){
		formModel.setProductDetail(productVO);
		FIELD_PROD_ID.setText(String.valueOf(productVO.getProductId()));
		FIELD_PROD_ID.setEditable(false);
		
		List<ProductUnitVO> productUnitVOs = productVO.getProductUnitVOs();
		int row = 0;
		for(ProductUnitVO productUnitVO : productUnitVOs){
			unitPriceTable.getModel().setValueAt(productUnitVO.getId(), row, 0);
			row++;
		}		
	}

	
	//called from the editor
	public void populateProductPanel(ProductVO productVO){
		formModel.setProductDetail(productVO);
		rootPanel.add(constructProductPanel());
		ComponentRegistry.instance().repaintComponents();
	}
	
	private JPanel constructProductPanel(){
		if(productPanel != null){
			rootPanel.remove(productPanel);
		}
		
		productPanel = new JPanel();
		productPanel.setPreferredSize(new Dimension(AppContext.getInstance().getWorkAreaWidth(), 180));
	
		
		int leftWidth = (AppContext.getInstance().getWorkAreaWidth() - 20)/2;
		
		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(leftWidth, 130));
		populateProductMetaData(leftPanel);
		
		int remainingArea = (AppContext.getInstance().getWorkAreaWidth()  - 20) - leftWidth;
		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(remainingArea, 130));
		//rightPanel.setBackground(Color.GRAY);
		populateUnitPrice(rightPanel, remainingArea);
		
		productPanel.add(leftPanel);
		productPanel.add(rightPanel);
			
		
		JPanel controlPanel = constructControlPanel();
		productPanel.add(controlPanel);
		
		return productPanel;
	}
	
	
	
	
	private JPanel constructControlPanel(){
		JPanel controlPanel = new JPanel();
		//controlPanel.setBackground(Color.GREEN);
		int width = AppContext.getInstance().getWorkAreaWidth();
		controlPanel.setPreferredSize(new Dimension(width, 30));
		
		int intermediateSpace = 50;
		int interColSpace = 50;
		
		
		Box controlPanelRow = Box.createHorizontalBox();
		controlPanelRow.add(Box.createHorizontalStrut(intermediateSpace));
		
		JButton saveBt = new JButton(" Save ");
		saveBt.addActionListener(new SaveProductListener());
		controlPanelRow.add(saveBt);

		controlPanelRow.add(Box.createHorizontalStrut(interColSpace));
		
		JButton resetBt = new JButton(" Reset ");
		controlPanelRow.add(resetBt);

		
		controlPanelRow.add(Box.createHorizontalStrut(intermediateSpace));
		controlPanel.add(controlPanelRow);
		
		return controlPanel;
	}
	
	private void populateProductMetaData(JPanel rightPanel){
	
		ProductDetailModel productDetail = formModel.getProductDetailModel();
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(3, 4, 5, 5));
		
		JLabel productIdLabel = new JLabel("Product ID");
		FIELD_PROD_ID = new JTextField(productDetail.getProductId(), 10);
		FIELD_PROD_ID.setEditable(false);
		
		topPanel.add(productIdLabel);
		topPanel.add(FIELD_PROD_ID);
		
		JLabel productNameLabel = new JLabel("Product Name");
		FIELD_PROD_NAME = new JTextField(productDetail.getProductName(), 12);
		
		topPanel.add(productNameLabel);
		topPanel.add(FIELD_PROD_NAME);
		
		JLabel productCodeLabel = new JLabel("Product Code");
		FIELD_PROD_CODE = new JTextField(productDetail.getProductCode(), 10);
		
		topPanel.add(productCodeLabel);
		topPanel.add(FIELD_PROD_CODE);
		
		JLabel statusLabel = new JLabel("Status");
		
		Vector<LiteComboModel> productStatusList = new Vector<LiteComboModel>();
		
		ProductStatusEnum[] statuses = ProductStatusEnum.values();
		for(ProductStatusEnum status : statuses){
			productStatusList.add(new LiteComboModel(status.getId(), status.toString()));
		}
		
		
		
		FIELD_PROD_STATUS = new JComboBox(productStatusList);
		FIELD_PROD_STATUS.setSelectedItem(new LiteComboModel(productDetail.getProductStatus()));
		FIELD_PROD_STATUS.setRenderer(new LiteComboModelRenderer());
				
		topPanel.add(statusLabel);
		topPanel.add(FIELD_PROD_STATUS);
		
		JLabel qtyLabel = new JLabel("Quantity Available");
		FIELD_PROD_QTY = new JTextField(productDetail.getQuantityAvailable(), 5);
		topPanel.add(qtyLabel);
		topPanel.add(FIELD_PROD_QTY);
		
		JLabel quantityUnitLabel = new JLabel("Quantity Unit");
		
		Vector<LiteComboModel> unitLs = new Vector<LiteComboModel>();
		List<UnitVO> unitVOs = formDSModel.getUnitVOs();
		for(UnitVO unitVO : unitVOs){
			unitLs.add(new LiteComboModel(unitVO.getId(), unitVO.getName()));
		}
		FIELD_PROD_QTY_UNIT = new JComboBox(unitLs);
		FIELD_PROD_QTY_UNIT.setSelectedItem(new LiteComboModel(productDetail.getQuantityUnit()));
		FIELD_PROD_QTY_UNIT.setRenderer(new LiteComboModelRenderer());
						
		topPanel.add(quantityUnitLabel);
		topPanel.add(FIELD_PROD_QTY_UNIT);
		
		rightPanel.add(topPanel);
		
		JPanel descriptionPanel = new JPanel();
		
		JLabel descriptionLabel = new JLabel("Description:");
		descriptionPanel.add(descriptionLabel);
				
		FIELD_PROD_DESC = new JTextArea(productDetail.getProductDesc(), 2, 35); 
		//textArea.setPreferredSize(new Dimension(5, 5));
		JScrollPane scrollPane = new JScrollPane(FIELD_PROD_DESC);
		FIELD_PROD_DESC.setLineWrap(true);
		descriptionPanel.add(scrollPane);
		
		rightPanel.add(descriptionPanel);
	}
	
	private void populateUnitPrice(JPanel productPanel, int workArea){
		
		int tableWidth = workArea - 10;
		/*Object[][] rows = {{10001l, "Primary", new LiteComboModel(1, "Box"), 1, 50.00, "", ""},
						   {10002l, "Secondary", new LiteComboModel(2, "No"), 0.5, 25.00, "", ""},
						   {10003l, "Secondary", new LiteComboModel(3, "Litre"), 0.75, 35.00, "", ""}
						   };*/
		
		ProductDetailModel productDetail = formModel.getProductDetailModel();
		Object[][] rows = productDetail.getUnitPriceRows();
		
		
		List<UnitVO> unitVOs = formDSModel.getUnitVOs();
		
		final Vector<LiteComboModel> unitLs = new Vector<LiteComboModel>();
		for(UnitVO unitVO : unitVOs){
			unitLs.add(new LiteComboModel(unitVO.getId(), unitVO.getName()));
		}
		JComboBox unitComboBox = new JComboBox(unitLs);
		
		ProductUnitPriceTableModel tableModel = new  ProductUnitPriceTableModel(rows);
		unitPriceTable = new JTable(tableModel.getDefaultTableModel());
		unitPriceTable.setRowHeight(20);
		unitPriceTable.setBorder(LineBorder.createBlackLineBorder());
		unitPriceTable.setRowSelectionAllowed(false);
		unitPriceTable.setPreferredScrollableViewportSize(new Dimension(tableWidth, 100));
		unitPriceTable.setFillsViewportHeight(true);
		
				
		//productTable.getColumn(UNIT_PRICE_HEADER[0]).setPreferredWidth(100);
		unitPriceTable.getColumn(UNIT_PRICE_HEADER[1]).setPreferredWidth(100);
		
		
		unitPriceTable.getColumn(UNIT_PRICE_HEADER[2]).setCellEditor(new DefaultCellEditor(unitComboBox));
		unitPriceTable.getColumn(UNIT_PRICE_HEADER[2]).setCellRenderer(new LiteComboModelCellRenderer());
		unitPriceTable.getColumn(UNIT_PRICE_HEADER[2]).setPreferredWidth(100);
		
		unitPriceTable.getColumn(UNIT_PRICE_HEADER[3]).setPreferredWidth(100);
		unitPriceTable.getColumn(UNIT_PRICE_HEADER[4]).setPreferredWidth(100);
		unitPriceTable.getColumn(UNIT_PRICE_HEADER[5]).setPreferredWidth(100);
		unitPriceTable.getColumn(UNIT_PRICE_HEADER[6]).setPreferredWidth(100);
		
		unitPriceTable.getColumn(UNIT_PRICE_HEADER[7]).setPreferredWidth(15);
		unitPriceTable.getColumn(UNIT_PRICE_HEADER[7]).setMinWidth(15);
		unitPriceTable.getColumn(UNIT_PRICE_HEADER[7]).setMaxWidth(15);
		unitPriceTable.getColumn(UNIT_PRICE_HEADER[7]).setCellRenderer(new ProductUnitPriceCloseRenderer());
		unitPriceTable.getColumn(UNIT_PRICE_HEADER[7]).setCellEditor(new ProductCellAddRemoveEditor(new JCheckBox(), ProductCellAddRemoveEditorEnum.REMOVE));
		
		unitPriceTable.getColumn(UNIT_PRICE_HEADER[8]).setPreferredWidth(15);
		unitPriceTable.getColumn(UNIT_PRICE_HEADER[8]).setMinWidth(15);
		unitPriceTable.getColumn(UNIT_PRICE_HEADER[8]).setMaxWidth(15);
		unitPriceTable.getColumn(UNIT_PRICE_HEADER[8]).setCellRenderer(new ProductUnitPriceAddRenderer());
		unitPriceTable.getColumn(UNIT_PRICE_HEADER[8]).setCellEditor(new ProductCellAddRemoveEditor(new JCheckBox(), ProductCellAddRemoveEditorEnum.ADD));
		
		unitPriceTable.removeColumn(unitPriceTable.getColumn(UNIT_PRICE_HEADER[0]));
		ProductTableAction productTblAction = new ProductTableAction(
				tableModel.getDefaultTableModel(), FIELD_PROD_QTY);
		
		TableCellListener cellListener = new TableCellListener(unitPriceTable, productTblAction);
		
		JScrollPane scrollPane = new JScrollPane(unitPriceTable);
		productPanel.add(scrollPane);
	}
	
	
	

}
