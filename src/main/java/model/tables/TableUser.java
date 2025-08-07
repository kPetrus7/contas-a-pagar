package model.tables;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

import model.Usuario;

public class TableUser extends AbstractTableModel {

	private ArrayList<Usuario> users;

	public TableUser(ArrayList<Usuario> users) {
		super();
		this.users = users;
	}

	private static final String[] COLUNAS = { "ID", "Usuários", "Prioridade" };

	@Override
	public String getColumnName(int columns) {
		return COLUNAS[columns];
	}

	@Override
	public int getRowCount() {
		return users.size();
	}

	@Override
	public int getColumnCount() {
		return COLUNAS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		Usuario user = users.get(rowIndex);
		switch (columnIndex) {
			case 0:
				return user.getId();
			case 1:
				return user.getLogin();
			case 2:
				return opcoes(user.getPrioridade());
			default:
				throw new IndexOutOfBoundsException("Coluna inválida: " + columnIndex);
		}
	}

	private String opcoes(int status) {

		String[] op = new String[] { "Usuário", "Administrador" };
		int max = op.length - 1;

		if (status > max)
			status = max;
		if (status < 0)
			status = 0;

		return op[status];
	}
}
