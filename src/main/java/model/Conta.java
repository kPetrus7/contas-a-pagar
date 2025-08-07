package model;

public class Conta {
	
	private int id;
	private String origem;
	private String descricao;
	private String dataEntrada;
	private String dataVencimento;
	private String rateio;
	private int estado;
	private double valor;
	
	public Conta() {
		
	}
	
	public Conta(int id, String origem, String descricao, String entrada, String vencimento, String rateio, double valor, int estado) {
		
		this.id = id;
		this.origem = origem;
		this.descricao = descricao;
		this.dataEntrada = entrada;
		this.dataVencimento = vencimento;
		this.rateio = rateio;
		this.estado = estado;
		this.valor = valor;
	}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

	    public String getOrigem() {
	        return origem;
	    }

	    public void setOrigem(String origem) {
	        this.origem = origem;
	    }

	    public String getDescricao() {
	        return descricao;
	    }

	    public void setDescricao(String descricao) {
	        this.descricao = descricao;
	    }

	    public String getEntrada() {
	        return dataEntrada;
	    }

	    public void setEntrada(String dataEntrada) {
	        this.dataEntrada = dataEntrada;
	    }

	    public String getVencimento() {
	        return dataVencimento;
	    }

	    public void setVencimento(String dataVencimento) {
	        this.dataVencimento = dataVencimento;
	    }

	    public String getRateio() {
	        return rateio;
	    }

	    public void setRateio(String rateio) {
	        this.rateio = rateio;
	    }

	    public int getEstado() {
	        return estado;
	    }

		public String getStringEstado() {
			return String.valueOf(estado);
		}

	    public void setEstado(int estado) {
	        this.estado = estado;
	    }

		public double getValor() {
			return valor;
		}

		public String getStringValor() {
			return String.valueOf(valor);
		}

	    public void setValor(double valor) {
	        this.valor = valor;
	    }

		public void printConta() {
			
			System.out.println("ID:" + this.id);
			System.out.println("Origem: " + this.origem);
			System.out.println("Descrição: " + this.descricao);
			System.out.println("Entrada: " + this.dataEntrada);
			System.out.println("Vencimento: " + this.dataVencimento);
			System.out.println("Rateio: " + this.rateio);
			System.out.println("Valor: " + this.valor);
			System.out.println("Estado: " + this.estado);
		}
}