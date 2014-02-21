package com.litetech.omt.ui.comp;

import static com.litetech.omt.ui.model.BankTableModel.BANKHEADER;
import static com.litetech.omt.ui.model.ChargeTableModel.CHARGE_HEADER;
import static com.litetech.omt.ui.model.PaymentModeTableModel.PAY_MODE_HEADER;
import static com.litetech.omt.ui.model.UnitTableModel.header;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import com.litetech.omt.constant.ChargeOperationEnum;
import com.litetech.omt.constant.OMTSwitchEnum;
import com.litetech.omt.ui.comp.editor.ConfigureProductEditor;
import com.litetech.omt.ui.comp.editor.DynamicComboCellEditor;
import com.litetech.omt.ui.comp.editor.SettingBankCellEditor;
import com.litetech.omt.ui.comp.editor.SettingChargeCellEditor;
import com.litetech.omt.ui.comp.editor.SettingPaymentModeCellEditor;
import com.litetech.omt.ui.comp.editor.UnitCellEditor;
import com.litetech.omt.ui.comp.renderer.EditProductRenderer;
import com.litetech.omt.ui.comp.renderer.LiteComboModelCellRenderer;
import com.litetech.omt.ui.comp.renderer.LiteComboModelRenderer;
import com.litetech.omt.ui.comp.renderer.SettingBankCellRenderer;
import com.litetech.omt.ui.comp.renderer.SettingChargeCellRenderer;
import com.litetech.omt.ui.comp.renderer.SettingPaymentModeCellRenderer;
import com.litetech.omt.ui.comp.renderer.SettingUnitCellRenderer;
import com.litetech.omt.ui.comp.renderer.SettingChargeCellRenderer.ChargeRendererEnum;
import com.litetech.omt.ui.comp.renderer.SettingUnitCellRenderer.UnitRendererEnum;
import com.litetech.omt.ui.model.BankTableModel;
import com.litetech.omt.ui.model.ChargeTableModel;
import com.litetech.omt.ui.model.LiteComboModel;
import com.litetech.omt.ui.model.PaymentModeTableModel;
import com.litetech.omt.ui.model.SettingFormModel;
import com.litetech.omt.ui.model.UnitTableModel;
import com.litetech.omt.vo.ChargeVO;

public class SettingForm extends JPanel implements PanelTabComp{
	
	private JPanel rootPanel;
	private SettingFormModel setttingFormModel; 
	
	private JPanel myPanel;
	@Override
	public void setMyPanelTab(JPanel myPanel) {
		this.myPanel = myPanel;	
	}


	@Override
	public JPanel getMyPanelTab() {
		return myPanel;
	}
	
	public SettingForm(SettingFormModel setttingFormModel){
		this.setttingFormModel = setttingFormModel;
		
		this.setBackground(Color.WHITE);
		rootPanel = new JPanel();
		rootPanel.setBackground(Color.WHITE);
		rootPanel.setPreferredSize(AppContext.getInstance().getBaseFormPanelDimension());
		
		rootPanel.add(constructUnitTaxComboPanel());
		rootPanel.add(constructBankModeComboPanel());
		//rootPanel.add(constructProductPanel());
		add(rootPanel);
	}

	private JPanel constructBankModeComboPanel(){
		JPanel bankModePanel = new JPanel();
		
		JPanel bankPanel = new JPanel();
		int midArea = ((AppContext.getInstance().getWorkAreaWidth() - 20) /  2) - 50;
		bankPanel.setPreferredSize(new Dimension(midArea, 250));
		//invoicePanel.setBackground(Color.WHITE);
		populateBankPanel(bankPanel, midArea);
		
		JPanel payModePanel = new JPanel();
		int chargeArea = ((AppContext.getInstance().getWorkAreaWidth() - 20) - midArea);
		//orderPanel.setBackground(Color.WHITE);
		payModePanel.setPreferredSize(new Dimension(chargeArea, 250));
		populatePaymentModePanel(payModePanel, chargeArea);
		
		
		bankModePanel.add(bankPanel);
		bankModePanel.add(payModePanel);
		
		return bankModePanel;
	}
	
	private void populateBankPanel(JPanel bankPanel, int totalArea){
		
		Vector<LiteComboModel> statusLs = new Vector<LiteComboModel>();
		OMTSwitchEnum[] switchEnums = OMTSwitchEnum.values();
		for(int ct = 0; ct < switchEnums.length; ct++){
			OMTSwitchEnum switchEnum = switchEnums[ct];
			statusLs.add(new LiteComboModel(switchEnum.getId(), switchEnum.getName()));
		}
		JComboBox statusCombo = new JComboBox(statusLs);
		statusCombo.setRenderer(new LiteComboModelRenderer());
		
		Object[][] rows = setttingFormModel.getBankRows();
		BankTableModel bankTableModel = new BankTableModel(rows);
		
		JTable bankTable = new JTable(bankTableModel.getDefaultTableModel());
		bankTable.setRowHeight(20);
		
		bankTable.setPreferredScrollableViewportSize(new Dimension(totalArea-10, 210));
		bankTable.setFillsViewportHeight(true);
		bankTable.setBorder(LineBorder.createBlackLineBorder());
		
		bankTable.getColumn(BANKHEADER[0]).setPreferredWidth(100);
		bankTable.getColumn(BANKHEADER[1]).setPreferredWidth(100);
		bankTable.getColumn(BANKHEADER[2]).setPreferredWidth(100);
		bankTable.getColumn(BANKHEADER[2]).setCellEditor(new DefaultCellEditor(statusCombo));
		bankTable.getColumn(BANKHEADER[2]).setCellRenderer(new LiteComboModelCellRenderer());
		
		bankTable.getColumn(BANKHEADER[3]).setPreferredWidth(15);
		bankTable.getColumn(BANKHEADER[3]).setMinWidth(15);
		bankTable.getColumn(BANKHEADER[3]).setMaxWidth(15);
		bankTable.getColumn(BANKHEADER[3]).setCellRenderer(new SettingBankCellRenderer(UnitRendererEnum.EDIT, bankTableModel));
		bankTable.getColumn(BANKHEADER[3]).setCellEditor(new SettingBankCellEditor(new JCheckBox(), bankTableModel, UnitRendererEnum.EDIT));
		
		bankTable.getColumn(BANKHEADER[4]).setPreferredWidth(15);
		bankTable.getColumn(BANKHEADER[4]).setMinWidth(15);
		bankTable.getColumn(BANKHEADER[4]).setMaxWidth(15);
		bankTable.getColumn(BANKHEADER[4]).setCellRenderer(new SettingBankCellRenderer(UnitRendererEnum.DELETE, bankTableModel));
		bankTable.getColumn(BANKHEADER[4]).setCellEditor(new SettingBankCellEditor(new JCheckBox(), bankTableModel, UnitRendererEnum.DELETE));
		
		bankTable.getColumn(BANKHEADER[5]).setPreferredWidth(15);
		bankTable.getColumn(BANKHEADER[5]).setMinWidth(15);
		bankTable.getColumn(BANKHEADER[5]).setMaxWidth(15);
		bankTable.getColumn(BANKHEADER[5]).setCellRenderer(new SettingBankCellRenderer(UnitRendererEnum.ADD, bankTableModel));
		bankTable.getColumn(BANKHEADER[5]).setCellEditor(new SettingBankCellEditor(new JCheckBox(), bankTableModel, UnitRendererEnum.ADD));
		
		
		JScrollPane scrollPane = new JScrollPane(bankTable);
		bankPanel.add(scrollPane);
	}
	
	
	
	private void populatePaymentModePanel(JPanel paymentModePanel, int totalArea){
		//chargePanel.setBackground(Color.RED);
		
		
		Vector<LiteComboModel> statusLs = new Vector<LiteComboModel>();
		OMTSwitchEnum[] switchEnums = OMTSwitchEnum.values();
		for(int ct = 0; ct < switchEnums.length; ct++){
			OMTSwitchEnum switchEnum = switchEnums[ct];
			statusLs.add(new LiteComboModel(switchEnum.getId(), switchEnum.getName()));
		}
		JComboBox statusCombo = new JComboBox(statusLs);
		statusCombo.setRenderer(new LiteComboModelRenderer());
		
		
		//Object[][] rows = {{10001l, "Service Charges 5 %", "5", ChargeOperationEnum.ADD, 1, "", "", ""}};
		Object[][] rows = setttingFormModel.getPaymentModeRows();
		PaymentModeTableModel paymentModeModel = new PaymentModeTableModel(rows);
		JTable paymentModeTable = new JTable(paymentModeModel.getDefaultTableModel());
		paymentModeTable.setRowHeight(20);
		
		paymentModeTable.setPreferredScrollableViewportSize(new Dimension(totalArea-10, 210));
		paymentModeTable.setFillsViewportHeight(true);
		paymentModeTable.setBorder(LineBorder.createBlackLineBorder());
		
		paymentModeTable.getColumn(PAY_MODE_HEADER[0]).setPreferredWidth(75);
		paymentModeTable.getColumn(PAY_MODE_HEADER[0]).setMinWidth(75);
		paymentModeTable.getColumn(PAY_MODE_HEADER[0]).setMaxWidth(75);
		
		paymentModeTable.getColumn(PAY_MODE_HEADER[1]).setPreferredWidth(100);
		paymentModeTable.getColumn(PAY_MODE_HEADER[2]).setPreferredWidth(100);
		
		paymentModeTable.getColumn(PAY_MODE_HEADER[3]).setPreferredWidth(75);
		paymentModeTable.getColumn(PAY_MODE_HEADER[3]).setMinWidth(75);
		paymentModeTable.getColumn(PAY_MODE_HEADER[3]).setMaxWidth(75);
		paymentModeTable.getColumn(PAY_MODE_HEADER[3]).setCellEditor(new DefaultCellEditor(statusCombo));
		paymentModeTable.getColumn(PAY_MODE_HEADER[3]).setCellRenderer(new LiteComboModelCellRenderer());
		
		paymentModeTable.getColumn(PAY_MODE_HEADER[4]).setPreferredWidth(100);
		paymentModeTable.getColumn(PAY_MODE_HEADER[5]).setPreferredWidth(15);
		paymentModeTable.getColumn(PAY_MODE_HEADER[5]).setMinWidth(15);
		paymentModeTable.getColumn(PAY_MODE_HEADER[5]).setMaxWidth(15);
		paymentModeTable.getColumn(PAY_MODE_HEADER[5]).setCellRenderer(new SettingPaymentModeCellRenderer(paymentModeModel, ChargeRendererEnum.EDIT));
		paymentModeTable.getColumn(PAY_MODE_HEADER[5]).setCellEditor(new SettingPaymentModeCellEditor(new JCheckBox(), paymentModeModel, ChargeRendererEnum.EDIT));
		
		paymentModeTable.getColumn(PAY_MODE_HEADER[6]).setPreferredWidth(15);
		paymentModeTable.getColumn(PAY_MODE_HEADER[6]).setMinWidth(15);
		paymentModeTable.getColumn(PAY_MODE_HEADER[6]).setMaxWidth(15);
		paymentModeTable.getColumn(PAY_MODE_HEADER[6]).setCellRenderer(new SettingPaymentModeCellRenderer(paymentModeModel, ChargeRendererEnum.DELETE));
		paymentModeTable.getColumn(PAY_MODE_HEADER[6]).setCellEditor(new SettingPaymentModeCellEditor(new JCheckBox(), paymentModeModel, ChargeRendererEnum.DELETE));
		
		paymentModeTable.getColumn(CHARGE_HEADER[7]).setPreferredWidth(15);
		paymentModeTable.getColumn(CHARGE_HEADER[7]).setMinWidth(15);
		paymentModeTable.getColumn(CHARGE_HEADER[7]).setMaxWidth(15);
		paymentModeTable.getColumn(CHARGE_HEADER[7]).setCellRenderer(new SettingPaymentModeCellRenderer(paymentModeModel, ChargeRendererEnum.ADD));
		paymentModeTable.getColumn(CHARGE_HEADER[7]).setCellEditor(new SettingPaymentModeCellEditor(new JCheckBox(), paymentModeModel, ChargeRendererEnum.ADD));
		
		JScrollPane scrollPane = new JScrollPane(paymentModeTable);
		paymentModePanel.add(scrollPane);
	}

	
	
	
	
	private JPanel constructUnitTaxComboPanel(){
		JPanel unitChargePanel = new JPanel();
		
		JPanel unitPanel = new JPanel();
		
		int midArea = ((AppContext.getInstance().getWorkAreaWidth() - 20) /  2) - 50;
		
		unitPanel.setPreferredSize(new Dimension(midArea, 250));
		//invoicePanel.setBackground(Color.WHITE);
		populateUnitPanel(unitPanel, midArea);
		
		JPanel chargePanel = new JPanel();
		int chargeArea = ((AppContext.getInstance().getWorkAreaWidth() - 20) - midArea);
		//orderPanel.setBackground(Color.WHITE);
		chargePanel.setPreferredSize(new Dimension(chargeArea, 250));
		populateChargePanel(chargePanel, chargeArea);
		
		
		unitChargePanel.add(unitPanel);
		unitChargePanel.add(chargePanel);
		
		return unitChargePanel;
	}
	
	
	private void populateUnitPanel(JPanel unitPanel, int totalArea){
		//unitPanel.setBackground(Color.GREEN);
		
		Object[][] rows = setttingFormModel.getUnitRows();
		UnitTableModel unitTableModel = new UnitTableModel(rows);
		
		JTable unitTable = new JTable(unitTableModel.getDefaultTableModel());
		unitTable.setRowHeight(20);
		
		unitTable.setPreferredScrollableViewportSize(new Dimension(totalArea-10, 210));
		unitTable.setFillsViewportHeight(true);
		unitTable.setBorder(LineBorder.createBlackLineBorder());
		
		unitTable.getColumn(header[0]).setPreferredWidth(100);
		unitTable.getColumn(header[1]).setPreferredWidth(100);
		unitTable.getColumn(header[2]).setPreferredWidth(100);
		
		unitTable.getColumn(header[3]).setPreferredWidth(15);
		unitTable.getColumn(header[3]).setMinWidth(15);
		unitTable.getColumn(header[3]).setMaxWidth(15);
		unitTable.getColumn(header[3]).setCellRenderer(new SettingUnitCellRenderer(UnitRendererEnum.EDIT, unitTableModel));
		unitTable.getColumn(header[3]).setCellEditor(new UnitCellEditor(new JCheckBox(), unitTableModel, UnitRendererEnum.EDIT));
		
		unitTable.getColumn(header[4]).setPreferredWidth(15);
		unitTable.getColumn(header[4]).setMinWidth(15);
		unitTable.getColumn(header[4]).setMaxWidth(15);
		unitTable.getColumn(header[4]).setCellRenderer(new SettingUnitCellRenderer(UnitRendererEnum.DELETE, unitTableModel));
		unitTable.getColumn(header[4]).setCellEditor(new UnitCellEditor(new JCheckBox(), unitTableModel, UnitRendererEnum.DELETE));
		
		unitTable.getColumn(header[5]).setPreferredWidth(15);
		unitTable.getColumn(header[5]).setMinWidth(15);
		unitTable.getColumn(header[5]).setMaxWidth(15);
		unitTable.getColumn(header[5]).setCellRenderer(new SettingUnitCellRenderer(UnitRendererEnum.ADD, unitTableModel));
		unitTable.getColumn(header[5]).setCellEditor(new UnitCellEditor(new JCheckBox(), unitTableModel, UnitRendererEnum.ADD));
		
		
		JScrollPane scrollPane = new JScrollPane(unitTable);
		unitPanel.add(scrollPane);
	}
	
	
	private Vector<LiteComboModel> constructOperationList(){
		Vector<LiteComboModel> operationCombos = new Vector<LiteComboModel>();
		
		
		operationCombos.add(new LiteComboModel(ChargeOperationEnum.ADD.getId(), ChargeOperationEnum.ADD.toString()));
		operationCombos.add(new LiteComboModel(ChargeOperationEnum.SUB.getId(), ChargeOperationEnum.SUB.toString()));
		
		return operationCombos;
	}
	
	
	private Vector<LiteComboModel> constructApplyCharegeList(List<ChargeVO> activeCharges){
		Vector<LiteComboModel> applyOnChargeLs = new Vector<LiteComboModel>();
		applyOnChargeLs.add(new LiteComboModel(-1, "Total"));
		
		for(ChargeVO chargeVO : activeCharges){
			applyOnChargeLs.add(new LiteComboModel(chargeVO.getId(), chargeVO.getName()));
		}
		
		return applyOnChargeLs;
	}
	
	private void populateChargePanel(JPanel chargePanel, int totalArea){
		//chargePanel.setBackground(Color.RED);
		
		
		final Vector<LiteComboModel> operationLs = constructOperationList();
		
		JComboBox operationComboBx = new JComboBox(operationLs);
		operationComboBx.setRenderer(new LiteComboModelRenderer());
		
		final Vector<LiteComboModel> chargeLs = constructApplyCharegeList(setttingFormModel.getChargeVOs());
		JComboBox activeChargeCombs = new JComboBox(chargeLs);
		activeChargeCombs.setRenderer(new LiteComboModelRenderer());
		
		//Object[][] rows = {{10001l, "Service Charges 5 %", "5", ChargeOperationEnum.ADD, 1, "", "", ""}};
		Object[][] rows = setttingFormModel.getChargeRows();
		ChargeTableModel chargeTableModel = new ChargeTableModel(rows);
		JTable chargeTable = new JTable(chargeTableModel.getDefaultTableModel());
		chargeTable.setRowHeight(20);
		
		chargeTable.setPreferredScrollableViewportSize(new Dimension(totalArea-10, 210));
		chargeTable.setFillsViewportHeight(true);
		chargeTable.setBorder(LineBorder.createBlackLineBorder());
		
		chargeTable.getColumn(CHARGE_HEADER[0]).setPreferredWidth(100);
		chargeTable.getColumn(CHARGE_HEADER[1]).setPreferredWidth(100);
			
		chargeTable.getColumn(CHARGE_HEADER[2]).setPreferredWidth(50);
		chargeTable.getColumn(CHARGE_HEADER[3]).setMinWidth(100);
		chargeTable.getColumn(CHARGE_HEADER[3]).setMaxWidth(100);
		
		chargeTable.getColumn(CHARGE_HEADER[3]).setPreferredWidth(75);
		chargeTable.getColumn(CHARGE_HEADER[3]).setMinWidth(75);
		chargeTable.getColumn(CHARGE_HEADER[3]).setMaxWidth(75);
		chargeTable.getColumn(CHARGE_HEADER[3]).setCellEditor(new DefaultCellEditor(operationComboBx));
		chargeTable.getColumn(CHARGE_HEADER[3]).setCellRenderer(new LiteComboModelCellRenderer());
		
		chargeTable.getColumn(CHARGE_HEADER[4]).setPreferredWidth(100);
		chargeTable.getColumn(CHARGE_HEADER[4]).setCellEditor(new DynamicComboCellEditor(chargeLs));
		chargeTable.getColumn(CHARGE_HEADER[4]).setCellRenderer(new LiteComboModelCellRenderer());	
		
		chargeTable.getColumn(CHARGE_HEADER[5]).setPreferredWidth(15);
		chargeTable.getColumn(CHARGE_HEADER[5]).setMinWidth(15);
		chargeTable.getColumn(CHARGE_HEADER[5]).setMaxWidth(15);
		chargeTable.getColumn(CHARGE_HEADER[5]).setCellRenderer(new SettingChargeCellRenderer(chargeTableModel, ChargeRendererEnum.EDIT));
		chargeTable.getColumn(CHARGE_HEADER[5]).setCellEditor(new SettingChargeCellEditor(new JCheckBox(), chargeTableModel, ChargeRendererEnum.EDIT));
		
		chargeTable.getColumn(CHARGE_HEADER[6]).setPreferredWidth(15);
		chargeTable.getColumn(CHARGE_HEADER[6]).setMinWidth(15);
		chargeTable.getColumn(CHARGE_HEADER[6]).setMaxWidth(15);
		chargeTable.getColumn(CHARGE_HEADER[6]).setCellRenderer(new SettingChargeCellRenderer(chargeTableModel, ChargeRendererEnum.DELETE));
		chargeTable.getColumn(CHARGE_HEADER[6]).setCellEditor(new SettingChargeCellEditor(new JCheckBox(), chargeTableModel, ChargeRendererEnum.DELETE));
		
		chargeTable.getColumn(CHARGE_HEADER[7]).setPreferredWidth(15);
		chargeTable.getColumn(CHARGE_HEADER[7]).setMinWidth(15);
		chargeTable.getColumn(CHARGE_HEADER[7]).setMaxWidth(15);
		chargeTable.getColumn(CHARGE_HEADER[7]).setCellRenderer(new SettingChargeCellRenderer(chargeTableModel, ChargeRendererEnum.ADD));
		chargeTable.getColumn(CHARGE_HEADER[7]).setCellEditor(new SettingChargeCellEditor(new JCheckBox(), chargeTableModel, ChargeRendererEnum.ADD));
		
		JScrollPane scrollPane = new JScrollPane(chargeTable);
		chargePanel.add(scrollPane);
	}
	
		
}
