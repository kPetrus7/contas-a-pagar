package model.tables;

import javax.swing.table.AbstractTableModel;

import model.Conta;
import model.ListaContas;
import view.Formats.Formatos;

public class TableConta extends AbstractTableModel {
	
	private ListaContas lista_contas;
	
	public TableConta() {

		super();
		lista_contas = ListaContas.getInstance();
	}
	
	private static final String[] COLUNAS = {"ID", "Origem", "Descri\u00E7\u00E3o", "Entrada", "Vencimento", "Rateio", "Valor (R$)", "Estado"};
	
	@Override
	public String getColumnName(int columns) {
		return COLUNAS[columns];
	}

	@Override
	public int getRowCount() {
		    return ListaContas.getInstance().getContas().size();
	}

	@Override
	public int getColumnCount() {
		return COLUNAS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

    	Conta conta = ListaContas.getInstance().getContas().get(rowIndex);

		switch (columnIndex) {
		case 0: return conta.getId();
        case 1: return conta.getOrigem();
        case 2: return conta.getDescricao();
        case 3: return Formatos.padraoData(String.valueOf(conta.getEntrada()));
        case 4: return Formatos.padraoData(String.valueOf(conta.getVencimento()));
        case 5: return conta.getRateio();
        case 6: return Formatos.padraMonetario(String.valueOf(conta.getValor()));
        case 7: return opcoes(conta.getEstado());
        default: throw new IndexOutOfBoundsException("Coluna inválida: " + columnIndex);
    	}
	}

	private String opcoes(int status) {

		String[] op = new String[] {"Aberto", "Pago", "Vencido"};	
		int max = op.length - 1;

		if (status > max) status = max;
		if (status < 0) status = 0;

		return op[status];
	}
}