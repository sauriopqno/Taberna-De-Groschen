package vista;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import modelo.*;
import consultas.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URL;

public class TabernaDeGroschen extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tablaMenu;
    private DefaultTableModel modeloCarrito;
    private double totalCuenta = 0.0;
    private Usuario usuarioActivo;
    private JLabel lblImagenProducto;
    private JTextArea txtDescripcion;
    private int xOffset = 0;
    private Timer timerAnimacion;

    public void cargarTablaMenu(String filtroCategoria) {
        DefaultTableModel modelo = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        modelo.addColumn("ID");
        modelo.addColumn("Platillo");
        modelo.addColumn("Descripción");
        modelo.addColumn("Categoría");
        modelo.addColumn("Precio ($)");

        ProductoSQL dao = new ProductoSQL();
        List<Producto> listaMenu = dao.listarProductos();

        for (Producto p : listaMenu) {
            if (filtroCategoria.equals("Todos") || p.getCategoria().equalsIgnoreCase(filtroCategoria)) {
                Object[] fila = { p.getId(), p.getNombre(), p.getDescripcion(), p.getCategoria(), p.getPrecio() };
                modelo.addRow(fila);
            }
        }

        tablaMenu.setModel(modelo);
        
        tablaMenu.getColumnModel().removeColumn(tablaMenu.getColumnModel().getColumn(2));

        botones.estilizarTabla(tablaMenu, new Color(50, 20, 0));
        tablaMenu.setRowHeight(40);
        tablaMenu.getColumnModel().getColumn(0).setPreferredWidth(40);
        tablaMenu.getColumnModel().getColumn(1).setPreferredWidth(250);
        tablaMenu.getColumnModel().getColumn(2).setPreferredWidth(120);
        tablaMenu.getColumnModel().getColumn(3).setPreferredWidth(80);
    }

    public TabernaDeGroschen(Usuario user) {
        this.usuarioActivo = user;
        setTitle("Taberna de Groschen - Bienvenido, " + user.getNombre());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1024, 768);
        setResizable(false);
        setLocationRelativeTo(null);

        contentPane = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                URL fondoURL = getClass().getResource("/imagenes/Fondo_taberna.png");
                if (fondoURL != null) {
                    Image imagen = new ImageIcon(fondoURL).getImage();
                    g.drawImage(imagen, xOffset, 0, getWidth(), getHeight(), this);
                    if (xOffset < 0) {
                        g.drawImage(imagen, xOffset + getWidth(), 0, getWidth(), getHeight(), this);
                    } else {
                        g.drawImage(imagen, xOffset - getWidth(), 0, getWidth(), getHeight(), this);
                    }
                }
            }
        };

        timerAnimacion = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xOffset -= 1;
                if (Math.abs(xOffset) >= getWidth()) {
                    xOffset = 0;
                }
                repaint();
            }
        });
        timerAnimacion.start();

        contentPane.setLayout(null);
        setContentPane(contentPane);

        JPanel panelFiltros = new JPanel();
        panelFiltros.setOpaque(false);
        panelFiltros.setBounds(30, 170, 650, 40);
        String[] categorias = {"Todos", "Banquetes", "Bebidas", "Postres", "Raciones"};

        for (String cat : categorias) {
            JButton btn = new JButton(cat);
            botones.estilizarBoton(btn);
            btn.addActionListener(e -> cargarTablaMenu(cat));
            panelFiltros.add(btn);
        }
        contentPane.add(panelFiltros);

        JPanel panelPergamino = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                URL url = getClass().getResource("/imagenes/Fondo_menu.png");
                if (url != null) g.drawImage(new ImageIcon(url).getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelPergamino.setOpaque(false);
        panelPergamino.setBounds(10, 210, 670, 450);
        panelPergamino.setLayout(null);

        JScrollPane scrollMenu = new JScrollPane();
        scrollMenu.setBounds(85, 50, 500, 350);
        scrollMenu.setOpaque(false);
        scrollMenu.getViewport().setOpaque(false);
        scrollMenu.setBorder(null);

        tablaMenu = new JTable();
        scrollMenu.setViewportView(tablaMenu);
        botones.estilizarScroll(scrollMenu);
        panelPergamino.add(scrollMenu);
        contentPane.add(panelPergamino);

        modeloCarrito = new DefaultTableModel(new Object[]{"ID", "Platillo", "Precio"}, 0);

        lblImagenProducto = new JLabel("");
        lblImagenProducto.setBounds(720, 210, 250, 250);
        lblImagenProducto.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblImagenProducto);

        txtDescripcion = new JTextArea();
        txtDescripcion.setBounds(720, 470, 250, 80);
        txtDescripcion.setOpaque(false);
        txtDescripcion.setForeground(Color.WHITE);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setEditable(false);
        txtDescripcion.setFocusable(false);
        contentPane.add(txtDescripcion);

        tablaMenu.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaMenu.getSelectedRow() != -1) {
                int modelRow = tablaMenu.getSelectedRow();
                String nombrePlatillo = tablaMenu.getValueAt(modelRow, 1).toString();
                String desc = tablaMenu.getModel().getValueAt(modelRow, 2).toString();
                actualizarImagen(nombrePlatillo);
                txtDescripcion.setText(desc);
            }
        });

        JButton btnAgregar = new JButton("Agregar a la Comanda");
        botones.estilizarBoton(btnAgregar);
        btnAgregar.setBounds(210, 670, 270, 40);
        btnAgregar.addActionListener(e -> agregarPlatilloAlCarrito());
        contentPane.add(btnAgregar);

        JButton btnVerComanda = new JButton("Ver Comanda y Pagar");
        botones.estilizarBoton(btnVerComanda);
        btnVerComanda.setBackground(new Color(120, 30, 20));
        btnVerComanda.setBounds(720, 560, 250, 50);
        btnVerComanda.addActionListener(e -> abrirVentanaComanda());
        contentPane.add(btnVerComanda);

        JButton btnSalir = new JButton("Abandonar la taberna");
        botones.estilizarBoton(btnSalir);
        btnSalir.setBounds(10, 683, 200, 30);
        btnSalir.addActionListener(e -> {
            if (botones.mostrarConfirmacion(TabernaDeGroschen.this, "¿Te vas, viajero?", "Salir") == JOptionPane.YES_OPTION) {
                new Login().setVisible(true);
                TabernaDeGroschen.this.dispose();
            }
        });
        contentPane.add(btnSalir);

        cargarTablaMenu("Todos");
    }

    private void agregarPlatilloAlCarrito() {
        int row = tablaMenu.getSelectedRow();
        if (row == -1) {
            botones.mostrarMensaje(this, "¡Selecciona un platillo del menú primero!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(tablaMenu.getValueAt(row, 0).toString());
        String nom = tablaMenu.getValueAt(row, 1).toString();
        String categoria = tablaMenu.getValueAt(row, 2).toString();
        double pre = Double.parseDouble(tablaMenu.getValueAt(row, 3).toString());
        final Vendible[] platillo = { new PlatilloBase(id, nom, pre) };

        JDialog dial = new JDialog(this, "Opciones de Cocina", true);
        dial.setSize(500, 250);
        dial.setLocationRelativeTo(this);
        dial.getContentPane().setBackground(new Color(60, 40, 20));
        dial.setLayout(new BorderLayout());

        JLabel lblPregunta = new JLabel("¿Qué agregados deseas, viajero? (Puedes elegir varios)", SwingConstants.CENTER);
        lblPregunta.setForeground(Color.WHITE);
        lblPregunta.setBorder(new EmptyBorder(15, 10, 15, 10));
        dial.add(lblPregunta, BorderLayout.NORTH);

        JPanel panelOpciones = new JPanel(new GridLayout(0, 2, 10, 10));
        panelOpciones.setOpaque(false);
        panelOpciones.setBorder(new EmptyBorder(0, 20, 10, 20));

        String[] opcionesNombres;
        if (categoria.equalsIgnoreCase("Bebidas")) {
            opcionesNombres = new String[]{"Hielo de Montaña (+$5)", "Doble Malta (+$20)", "Jarra de Cuerno (+$15)"};
        } else if (categoria.equalsIgnoreCase("Postres")) {
            opcionesNombres = new String[]{"Frutos Rojos (+$10)", "Miel de Bohemia (+$15)", "Extra Nata (+$10)"};
        } else {
            opcionesNombres = new String[]{"Pan Extra (+$15)", "Ración Rey (+$40)", "Especias (+$25)", "En Zurrón (+$5)"};
        }

        java.util.List<JCheckBox> listaCasillas = new java.util.ArrayList<>();
        for (String nombreOpcion : opcionesNombres) {
            JCheckBox chk = new JCheckBox(nombreOpcion);
            chk.setOpaque(false);
            chk.setForeground(Color.WHITE);
            chk.setFocusPainted(false);
            listaCasillas.add(chk);
            panelOpciones.add(chk);
        }

        dial.add(panelOpciones, BorderLayout.CENTER);

        JPanel panelAbajo = new JPanel();
        panelAbajo.setOpaque(false);
        panelAbajo.setBorder(new EmptyBorder(10, 10, 15, 10));

        JButton btnConfirmar = new JButton("Añadir a la cuenta");
        botones.estilizarBoton(btnConfirmar);
        btnConfirmar.addActionListener(ev -> {
            for (JCheckBox casilla : listaCasillas) {
                if (casilla.isSelected()) {
                    String d = casilla.getText();
                    if (d.contains("Pan")) platillo[0] = new ExtraPan(platillo[0]);
                    else if (d.contains("Rey")) platillo[0] = new RacionReal(platillo[0]);
                    else if (d.contains("Especias")) platillo[0] = new EspeciasDeBohemia(platillo[0]);
                    else if (d.contains("Zurrón")) platillo[0] = new EnZurron(platillo[0]);
                    else if (d.contains("Hielo")) platillo[0] = new HieloMontana(platillo[0]);
                    else if (d.contains("Doble Malta")) platillo[0] = new DobleMalta(platillo[0]);
                    else if (d.contains("Cuerno")) platillo[0] = new JarraCuerno(platillo[0]);
                    else if (d.contains("Frutos")) platillo[0] = new FrutosRojos(platillo[0]);
                    else if (d.contains("Miel")) platillo[0] = new MielBohemia(platillo[0]);
                    else if (d.contains("Nata")) platillo[0] = new ExtraNata(platillo[0]);
                }
            }
            dial.dispose();
        });

        panelAbajo.add(btnConfirmar);
        dial.add(panelAbajo, BorderLayout.SOUTH);
        dial.setVisible(true);

        modeloCarrito.addRow(new Object[]{id, platillo[0].getNombre(), platillo[0].getPrecio()});
        totalCuenta += platillo[0].getPrecio();

        botones.mostrarMensaje(this, "¡" + platillo[0].getNombre() + " añadido a la comanda!", "Cocina", JOptionPane.INFORMATION_MESSAGE);
    }

    private void abrirVentanaComanda() {
        JDialog dialogCarrito = new JDialog(this, "Tu Comanda Actual", true);
        dialogCarrito.setSize(750, 480);
        dialogCarrito.setLocationRelativeTo(this);
        dialogCarrito.setResizable(false);

        JPanel panelFondo = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                URL url = getClass().getResource("/imagenes/Carruaje_delivery.png");
                if (url != null) {
                    Image img = new ImageIcon(url).getImage();
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panelFondo.setLayout(null);
        dialogCarrito.setContentPane(panelFondo);

        JPanel panelContenido = new JPanel();
        panelContenido.setBounds(20, 20, 400, 400);
        panelContenido.setBackground(new Color(25, 15, 10, 215));
        panelContenido.setBorder(BorderFactory.createLineBorder(new Color(180, 140, 50), 1));
        panelContenido.setLayout(null);
        panelFondo.add(panelContenido);

        JLabel titulo = new JLabel("Bienes a Pagar", SwingConstants.CENTER);
        titulo.setForeground(new Color(220, 180, 80));
        titulo.setFont(new Font(titulo.getFont().getName(), Font.BOLD, 18));
        titulo.setBounds(10, 15, 380, 25);
        panelContenido.add(titulo);

        JScrollPane scrollCarrito = new JScrollPane();
        scrollCarrito.setBounds(20, 50, 360, 230);
        scrollCarrito.setOpaque(false);
        scrollCarrito.getViewport().setOpaque(false);

        JTable tablaCarrito = new JTable(modeloCarrito);
        tablaCarrito.setRowHeight(35);
        botones.estilizarTabla(tablaCarrito, Color.WHITE);
        
        tablaCarrito.getColumnModel().getColumn(0).setPreferredWidth(30);
        tablaCarrito.getColumnModel().getColumn(1).setPreferredWidth(230);
        tablaCarrito.getColumnModel().getColumn(2).setPreferredWidth(70);

        botones.estilizarScroll(scrollCarrito);
        scrollCarrito.setViewportView(tablaCarrito);
        panelContenido.add(scrollCarrito);

        JLabel lblTotalDialogo = new JLabel("Total: $" + totalCuenta);
        lblTotalDialogo.setForeground(Color.WHITE);
        lblTotalDialogo.setFont(new Font(lblTotalDialogo.getFont().getName(), Font.BOLD, 16));
        lblTotalDialogo.setBounds(20, 295, 180, 25);
        panelContenido.add(lblTotalDialogo);

        JButton btnQuitar = new JButton("Quitar Platillo");
        botones.estilizarBoton(btnQuitar);
        btnQuitar.setBounds(230, 290, 150, 30);
        btnQuitar.addActionListener(e -> {
            int seleccion = tablaCarrito.getSelectedRow();
            if (seleccion == -1) {
                botones.mostrarMensaje(dialogCarrito, "Selecciona un platillo para quitarlo.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            double precioRestar = Double.parseDouble(tablaCarrito.getValueAt(seleccion, 2).toString());
            totalCuenta -= precioRestar;
            if (totalCuenta < 0) totalCuenta = 0.0;
            lblTotalDialogo.setText("Total: $" + totalCuenta);
            modeloCarrito.removeRow(seleccion);
        });
        panelContenido.add(btnQuitar);

        JButton btnPagar = new JButton("Proceder al Pago");
        botones.estilizarBoton(btnPagar);
        btnPagar.setBackground(new Color(20, 80, 40));
        btnPagar.setBounds(20, 340, 360, 45);
        btnPagar.addActionListener(e -> {
            if (totalCuenta == 0) {
                botones.mostrarMensaje(dialogCarrito, "Tu comanda está vacía, viajero.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            dialogCarrito.dispose();
            mostrarOpcionesDePago();
        });
        panelContenido.add(btnPagar);

        dialogCarrito.setVisible(true);
    }

    private void mostrarOpcionesDePago() {
        JDialog diagPago = new JDialog(this, "La Tesorería", true);
        diagPago.setSize(420, 260);
        diagPago.setLocationRelativeTo(this);
        diagPago.getContentPane().setBackground(new Color(40, 20, 10));
        diagPago.setLayout(null);

        JLabel lblPregunta = new JLabel("¿Cómo deseas liquidar tu cuenta de $" + totalCuenta + "?", SwingConstants.CENTER);
        lblPregunta.setForeground(Color.WHITE);
        lblPregunta.setFont(new Font(lblPregunta.getFont().getName(), Font.BOLD, 15));
        lblPregunta.setBounds(20, 25, 360, 30);
        diagPago.add(lblPregunta);

        JButton btnEfectivo = new JButton("Efectivo (Monedas de oro)");
        botones.estilizarBoton(btnEfectivo);
        btnEfectivo.setBounds(60, 75, 280, 45);
        btnEfectivo.addActionListener(e -> {
            diagPago.dispose();
            realizarPago();
        });
        diagPago.add(btnEfectivo);

        JButton btnTarjeta = new JButton("Tarjeta Mágica");
        botones.estilizarBoton(btnTarjeta);
        btnTarjeta.setBounds(60, 140, 280, 45);
        btnTarjeta.addActionListener(e -> {
            diagPago.dispose();
            mostrarFormularioTarjeta();
        });
        diagPago.add(btnTarjeta);

        diagPago.setVisible(true);
    }

    private void mostrarFormularioTarjeta() {
        JDialog diagTarjeta = new JDialog(this, "Datos de la Tarjeta", true);
        diagTarjeta.setSize(350, 300);
        diagTarjeta.setLocationRelativeTo(this);
        diagTarjeta.getContentPane().setBackground(new Color(25, 15, 10));
        diagTarjeta.setLayout(null);

        JLabel lblNum = new JLabel("Número de Tarjeta (16 dígitos):");
        lblNum.setForeground(Color.WHITE);
        lblNum.setBounds(30, 20, 280, 20);
        diagTarjeta.add(lblNum);

        JTextField txtNum = new JTextField();
        txtNum.setBounds(30, 45, 270, 30);
        diagTarjeta.add(txtNum);

        JLabel lblFecha = new JLabel("Fecha (MM/YY):");
        lblFecha.setForeground(Color.WHITE);
        lblFecha.setBounds(30, 90, 120, 20);
        diagTarjeta.add(lblFecha);

        JTextField txtFecha = new JTextField();
        txtFecha.setBounds(30, 115, 100, 30);
        diagTarjeta.add(txtFecha);

        JLabel lblCvv = new JLabel("CVV:");
        lblCvv.setForeground(Color.WHITE);
        lblCvv.setBounds(180, 90, 80, 20);
        diagTarjeta.add(lblCvv);

        JPasswordField txtCvv = new JPasswordField();
        txtCvv.setBounds(180, 115, 120, 30);
        diagTarjeta.add(txtCvv);

        JButton btnValidar = new JButton("Pagar Cuenta");
        botones.estilizarBoton(btnValidar);
        btnValidar.setBounds(30, 180, 270, 40);
        btnValidar.addActionListener(e -> {
            String num = txtNum.getText().trim();
            String fecha = txtFecha.getText().trim();
            String cvv = new String(txtCvv.getPassword()).trim();

            if (!num.matches("^\\d{16}$")) {
                botones.mostrarMensaje(diagTarjeta, "El número de tarjeta debe tener 16 dígitos exactos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!fecha.matches("^(0[1-9]|1[0-2])/\\d{2}$")) {
                botones.mostrarMensaje(diagTarjeta, "La fecha debe tener formato MM/YY (ej. 12/25).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!cvv.matches("^\\d{3,4}$")) {
                botones.mostrarMensaje(diagTarjeta, "El CVV debe tener 3 o 4 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            botones.mostrarMensaje(diagTarjeta, "¡Pago con tarjeta autorizado por la corona!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            diagTarjeta.dispose();
            realizarPago();
        });
        diagTarjeta.add(btnValidar);

        diagTarjeta.setVisible(true);
    }

    private void realizarPago() {
        double totalFinal;
        if (usuarioActivo.getEs_primera() == 1) {
            totalFinal = totalCuenta * 0.8;
            botones.mostrarMensaje(this, "¡Descuento de viajero nuevo aplicado (-20%)!", "Promoción", JOptionPane.INFORMATION_MESSAGE);
            usuarioActivo.setEs_primera(0);
            new UsuarioSQL().actualizaUsuario(usuarioActivo);
        } else {
            totalFinal = totalCuenta;
        }

        new PedidoSQL().registrarTicket(usuarioActivo, totalFinal, modeloCarrito);
        botones.mostrarMensaje(this, "¡Pedido enviado a la forja! Total cobrado: $" + totalFinal, "Pago Completado", JOptionPane.INFORMATION_MESSAGE);

        totalCuenta = 0;
        modeloCarrito.setRowCount(0);
    }

    private void actualizarImagen(String nombre) {
        URL url = getClass().getResource("/imagenes/" + nombre.toLowerCase().replace(" ", "_") + ".png");
        if (url != null) {
            Image img = new ImageIcon(url).getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            lblImagenProducto.setIcon(new ImageIcon(img));
        } else {
            lblImagenProducto.setIcon(null);
        }
    }
}