package vista;

import java.awt.Color;
import javax.swing.JButton;


public class botones {
	public static void estilizarBoton(JButton boton) {
	    boton.setUI(new javax.swing.plaf.basic.BasicButtonUI());
	    boton.setBackground(new Color(60, 40, 20)); 
	    boton.setForeground(Color.WHITE); 
	    boton.setFocusPainted(false); 
	    boton.setOpaque(true);
	    boton.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(180, 140, 50), 2));
	    boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	}
	
	public static void estilizarScroll(javax.swing.JScrollPane scrollPane) {
	    javax.swing.JScrollBar customScrollBar = new javax.swing.JScrollBar(javax.swing.JScrollBar.VERTICAL);
	    customScrollBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
	        @Override
	        protected void paintTrack(java.awt.Graphics g, javax.swing.JComponent c, java.awt.Rectangle trackBounds) {
	            g.setColor(new java.awt.Color(40, 20, 10));
	            g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
	        }
	        
	        @Override
	        protected void paintThumb(java.awt.Graphics g, javax.swing.JComponent c, java.awt.Rectangle thumbBounds) {
	            g.setColor(new java.awt.Color(120, 80, 30));
	            g.fillRect(thumbBounds.x + 2, thumbBounds.y + 2, thumbBounds.width - 4, thumbBounds.height - 4);
	        }

	        @Override
	        protected javax.swing.JButton createDecreaseButton(int orientation) {
	            return crearBotonVacio();
	        }

	        @Override
	        protected javax.swing.JButton createIncreaseButton(int orientation) {
	            return crearBotonVacio();
	        }

	        private javax.swing.JButton crearBotonVacio() {
	            javax.swing.JButton boton = new javax.swing.JButton();
	            boton.setPreferredSize(new java.awt.Dimension(0, 0));
	            boton.setMinimumSize(new java.awt.Dimension(0, 0));
	            boton.setMaximumSize(new java.awt.Dimension(0, 0));
	            return boton;
	        }
	    });

	    customScrollBar.setPreferredSize(new java.awt.Dimension(12, 0));
	    customScrollBar.setBorder(null);
	    scrollPane.setVerticalScrollBar(customScrollBar);
	    scrollPane.getCorner(javax.swing.JScrollPane.UPPER_RIGHT_CORNER);
	}
	
	public static void estilizarTabla(javax.swing.JTable tabla, java.awt.Color colorTexto) {
	    tabla.setOpaque(false);
	    tabla.setBackground(new java.awt.Color(0, 0, 0, 0));
	    tabla.setForeground(colorTexto);
	    tabla.setShowGrid(false);
	    tabla.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	    tabla.getTableHeader().setOpaque(true);
	    tabla.getTableHeader().setBackground(new java.awt.Color(60, 40, 20)); 
	    tabla.getTableHeader().setBorder(javax.swing.BorderFactory.createEmptyBorder()); 

	    javax.swing.table.DefaultTableCellRenderer headerRenderer = new javax.swing.table.DefaultTableCellRenderer();
	    headerRenderer.setOpaque(true);
	    headerRenderer.setBackground(new java.awt.Color(60, 40, 20)); 
	    headerRenderer.setForeground(java.awt.Color.WHITE); 
	    headerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);
	    headerRenderer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(180, 140, 50), 1)); 
	    tabla.getTableHeader().setDefaultRenderer(headerRenderer);
	    javax.swing.table.DefaultTableCellRenderer cellRenderer = new javax.swing.table.DefaultTableCellRenderer() {
	        @Override
	        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	            java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	            
	            if (isSelected) {
	                c.setBackground(new java.awt.Color(120, 80, 30, 150));
	                c.setForeground(java.awt.Color.WHITE);
	            } else {
	                c.setBackground(new java.awt.Color(0, 0, 0, 0));
	                c.setForeground(colorTexto);
	            }
	            ((javax.swing.JLabel)c).setOpaque(isSelected); 
	            return c;
	        }
	    };
	    tabla.setDefaultRenderer(Object.class, cellRenderer);
	    tabla.setDefaultRenderer(Number.class, cellRenderer);
	    if (tabla.getParent() instanceof javax.swing.JViewport) {
	        javax.swing.JViewport viewport = (javax.swing.JViewport) tabla.getParent();
	        viewport.setOpaque(false);
	        viewport.setBackground(new java.awt.Color(0, 0, 0, 0));
	    }
	}
	
		public static void estilizarFondoDialogo(java.awt.Component comp) {
		    comp.setBackground(new java.awt.Color(60, 40, 20));
		    
		    if (comp instanceof java.awt.Container) {
		        java.awt.Component[] hijos = ((java.awt.Container) comp).getComponents();
		        for (java.awt.Component hijo : hijos) {
		            if (hijo instanceof javax.swing.JPanel || hijo instanceof javax.swing.JLabel) {
		                estilizarFondoDialogo(hijo);
		                
		                if (hijo instanceof javax.swing.JLabel) {
		                    ((javax.swing.JLabel) hijo).setForeground(java.awt.Color.WHITE);
		                }
		            }
		        }
		    }
		}

		public static int mostrarConfirmacion(java.awt.Component parent, String mensaje, String titulo) {
		    javax.swing.JButton btnSi = new javax.swing.JButton("Sí");
		    javax.swing.JButton btnNo = new javax.swing.JButton("No");
		    
		    estilizarBoton(btnSi);
		    estilizarBoton(btnNo);

		    Object[] opciones = {btnSi, btnNo};

		    javax.swing.JOptionPane optionPane = new javax.swing.JOptionPane(
		            mensaje, 
		            javax.swing.JOptionPane.QUESTION_MESSAGE,
		            javax.swing.JOptionPane.YES_NO_OPTION, 
		            null, 
		            opciones, 
		            opciones[0]
		    );

		    estilizarFondoDialogo(optionPane);
		    
		    javax.swing.JDialog dialog = optionPane.createDialog(parent, titulo);
		    dialog.getContentPane().setBackground(new java.awt.Color(60, 40, 20));
		    final int[] resultado = {javax.swing.JOptionPane.CLOSED_OPTION};

		    btnSi.addActionListener(new java.awt.event.ActionListener() {
		        @Override
		        public void actionPerformed(java.awt.event.ActionEvent e) {
		            resultado[0] = javax.swing.JOptionPane.YES_OPTION;
		            dialog.dispose();
		        }
		    });

		    btnNo.addActionListener(new java.awt.event.ActionListener() {
		        @Override
		        public void actionPerformed(java.awt.event.ActionEvent e) {
		            resultado[0] = javax.swing.JOptionPane.NO_OPTION;
		            dialog.dispose();
		        }
		    });

		    dialog.setVisible(true);
		    return resultado[0];
		}
		public static void mostrarMensaje(java.awt.Component parent, String mensaje, String titulo, int tipoMensaje) {
		    javax.swing.JButton btnOk = new javax.swing.JButton("Entendido");
		    estilizarBoton(btnOk);

		    Object[] opciones = {btnOk};

		    javax.swing.JOptionPane optionPane = new javax.swing.JOptionPane(
		            mensaje, 
		            tipoMensaje,
		            javax.swing.JOptionPane.DEFAULT_OPTION, 
		            null, 
		            opciones, 
		            opciones[0]
		    );

		    estilizarFondoDialogo(optionPane);
		    
		    javax.swing.JDialog dialog = optionPane.createDialog(parent, titulo);
		    dialog.getContentPane().setBackground(new java.awt.Color(60, 40, 20));

		    btnOk.addActionListener(new java.awt.event.ActionListener() {
		        @Override
		        public void actionPerformed(java.awt.event.ActionEvent e) {
		            dialog.dispose();
		        }
		    });

		    dialog.setVisible(true);
		}
	}
