package vista;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import consultas.PedidoSQL;
import estados.EstadoEnBarra;
import estados.EstadoEnForja;
import estados.EstadoEntregado;
import modelo.Pedido;

public class CocinaGroschen extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tablaCocina;
    private PedidoSQL daoPedido = new PedidoSQL();

    public void cargarPedidos() {
        DefaultTableModel modelo = daoPedido.listarPedidosActivos();
        tablaCocina.setModel(modelo);
        botones.estilizarTabla(tablaCocina, Color.WHITE);
        tablaCocina.setRowHeight(40);
        if (tablaCocina.getColumnCount() > 0) {
            tablaCocina.getColumnModel().getColumn(0).setPreferredWidth(50);
        }
    }

    public CocinaGroschen() {
        setTitle("La Forja - Taberna del Groschen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        contentPane = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                java.net.URL fondoURL = getClass().getResource("/imagenes/Fondo_taberna_cocina.png");
                if (fondoURL != null) {
                    java.awt.Image imagen = new javax.swing.ImageIcon(fondoURL).getImage();
                    g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
                }
                g.setColor(new Color(0, 0, 0, 150)); 
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 30, 720, 400);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        botones.estilizarScroll(scrollPane);
        contentPane.add(scrollPane);

        tablaCocina = new JTable();
        tablaCocina.setRowHeight(30);
        scrollPane.setViewportView(tablaCocina);

        JButton btnAvanzar = new JButton("Avanzar Pedido (Cocinar/Entregar)");
        botones.estilizarBoton(btnAvanzar);
        btnAvanzar.setBounds(30, 450, 370, 40);
        contentPane.add(btnAvanzar);

        JButton btnCancelar = new JButton("Cancelar Pedido");
        botones.estilizarBoton(btnCancelar);
        btnCancelar.setBounds(433, 450, 317, 40);
        contentPane.add(btnCancelar);
        JButton btnVolver = new JButton("Volver al Login");
        botones.estilizarBoton(btnVolver);
        btnVolver.setBounds(30, 500, 720, 30);
        contentPane.add(btnVolver);
        
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int respuesta = botones.mostrarConfirmacion(CocinaGroschen.this, "¿Estás seguro de que deseas salir de la Forja y volver al Login?", "Cerrar Sesión");
                
                if (respuesta == JOptionPane.YES_OPTION) {
                    Login ventanaLogin = new Login();
                    ventanaLogin.setVisible(true);
                    CocinaGroschen.this.dispose();
                }
            }
        });

        btnAvanzar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int seleccion = tablaCocina.getSelectedRow();
                if (seleccion == -1) {
                	botones.mostrarMensaje(CocinaGroschen.this, "Selecciona un ticket primero.", "Aviso de la Forja", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int idPedido = Integer.parseInt(tablaCocina.getValueAt(seleccion, 0).toString());
                double total = Double.parseDouble(tablaCocina.getValueAt(seleccion, 3).toString());
                String estadoString = tablaCocina.getValueAt(seleccion, 4).toString();
                Pedido pedidoTemp = new Pedido(idPedido, total);
                
                if (estadoString.equals("En Barra")) {
                    pedidoTemp.setEstadoActual(new EstadoEnBarra());
                } else if (estadoString.equals("En Forja")) {
                    pedidoTemp.setEstadoActual(new EstadoEnForja());
                } else {
                    pedidoTemp.setEstadoActual(new EstadoEntregado());
                }
                pedidoTemp.avanzarEstado();
                String nuevoEstado = pedidoTemp.getEstadoActual().getNombreEstado();
                daoPedido.actualizarEstadoBD(idPedido, nuevoEstado);
                cargarPedidos();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int seleccion = tablaCocina.getSelectedRow();
                if (seleccion == -1) return;

                int idPedido = Integer.parseInt(tablaCocina.getValueAt(seleccion, 0).toString());
                String estadoString = tablaCocina.getValueAt(seleccion, 4).toString();

                Pedido pedidoTemp = new Pedido(idPedido, 0);
                if (estadoString.equals("En Barra")) pedidoTemp.setEstadoActual(new EstadoEnBarra());
                else if (estadoString.equals("En Forja")) pedidoTemp.setEstadoActual(new EstadoEnForja());
                pedidoTemp.cancelarPedido();
                
                if (pedidoTemp.getEstadoActual().getNombreEstado().equals("En Barra")) {
                     daoPedido.actualizarEstadoBD(idPedido, "Cancelado"); 
                     cargarPedidos();
                }
            }
        });
        
        cargarPedidos();
    }
}