package vista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import consultas.UsuarioSQL;
import modelo.Usuario;

public class Register extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField nombre;
    private JTextField correo;
    private JTextField telefono;
    private JTextField usuario;
    private JPasswordField contraseña;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Register frame = new Register();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Register() {
        setTitle("Registrar Viajero - Taberna del Groschen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 620);
        setResizable(false);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                URL fondoURL = getClass().getResource("/imagenes/Fondo_taberna_fuera.png");
                if (fondoURL != null) {
                    Image imagen = new javax.swing.ImageIcon(fondoURL).getImage();
                    g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JPanel panelCard = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(25, 15, 10, 215)); 
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelCard.setOpaque(false);
        panelCard.setBorder(BorderFactory.createLineBorder(new Color(180, 140, 50), 1));
        panelCard.setBounds(50, 25, 380, 530);
        panelCard.setLayout(null);
        contentPane.add(panelCard);

        
        JLabel lblNewLabel = new JLabel("Nombre y Apellido:");
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setBounds(40, 25, 300, 20);
        panelCard.add(lblNewLabel);
        
        nombre = new JTextField();
        nombre.setBounds(40, 50, 300, 30);
        panelCard.add(nombre);
        nombre.setColumns(10);
        
        JLabel lblNewLabel_1 = new JLabel("Correo electrónico:");
        lblNewLabel_1.setForeground(Color.WHITE);
        lblNewLabel_1.setBounds(40, 95, 300, 20);
        panelCard.add(lblNewLabel_1);
        
        correo = new JTextField();
        correo.setColumns(10);
        correo.setBounds(40, 120, 300, 30);
        panelCard.add(correo);
        
        JLabel lblNewLabel_2 = new JLabel("Número de teléfono:");
        lblNewLabel_2.setForeground(Color.WHITE);
        lblNewLabel_2.setBounds(40, 165, 300, 20);
        panelCard.add(lblNewLabel_2);
        
        telefono = new JTextField();
        telefono.setColumns(10);
        telefono.setBounds(40, 190, 300, 30);
        panelCard.add(telefono);
        
        JLabel lblNewLabel_3 = new JLabel("Nombre de usuario:");
        lblNewLabel_3.setForeground(Color.WHITE);
        lblNewLabel_3.setBounds(40, 235, 300, 20);
        panelCard.add(lblNewLabel_3);
        
        usuario = new JTextField();
        usuario.setColumns(10);
        usuario.setBounds(40, 260, 300, 30);
        panelCard.add(usuario);
        
        JLabel lblNewLabel_4 = new JLabel("Password:");
        lblNewLabel_4.setForeground(Color.WHITE);
        lblNewLabel_4.setBounds(40, 305, 300, 20);
        panelCard.add(lblNewLabel_4);
        
        contraseña = new JPasswordField();
        contraseña.setColumns(10);
        contraseña.setBounds(40, 330, 300, 30);
        contraseña.setEchoChar('*');
        panelCard.add(contraseña);
        
        
        JButton btnNewButton = new JButton("Completar Registro");
        botones.estilizarBoton(btnNewButton);
        btnNewButton.setBounds(40, 395, 300, 35);
        panelCard.add(btnNewButton);
        
        JLabel lblVolver = new JLabel("¿Ya eres miembro de la taberna?");
        lblVolver.setForeground(new Color(200, 200, 200));
        lblVolver.setHorizontalAlignment(SwingConstants.CENTER);
        lblVolver.setBounds(40, 445, 300, 20);
        panelCard.add(lblVolver);
        
        JButton btnVolver = new JButton("Volver al Login");
        botones.estilizarBoton(btnVolver);
        btnVolver.setBounds(100, 475, 180, 30);
        panelCard.add(btnVolver);
        
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nombre.getText().trim();
                String email = correo.getText().trim();
                String num = telefono.getText().trim();
                String user = usuario.getText().trim();
                String pass = new String(contraseña.getPassword());

                if (name.isEmpty() || email.isEmpty() || num.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                    botones.mostrarMensaje(Register.this, 
                        "Debes llenar todos los campos antes de registrarte.", 
                        "Campos incompletos", 
                        JOptionPane.WARNING_MESSAGE);
                    return; 
                }
                boolean correoValido = false;
                if (email.contains("@") && email.contains(".")) {
                    int indiceArroba = email.indexOf("@");
                    int ultimoPunto = email.lastIndexOf(".");
                    if (indiceArroba > 0 && ultimoPunto > (indiceArroba + 1) && ultimoPunto < (email.length() - 1)) {
                        correoValido = true;
                    }
                }
                
                if (!correoValido) {
                    botones.mostrarMensaje(Register.this, 
                        "El correo electrónico no es válido. Asegúrate de incluir '@' y un dominio.", 
                        "Correo inválido", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                boolean telefonoValido = true;
                if (num.length() != 10) {
                    telefonoValido = false;
                } else {
                    for (int i = 0; i < num.length(); i++) {
                        if (!Character.isDigit(num.charAt(i))) {
                            telefonoValido = false;
                            break;
                        }
                    }
                }
                
                if (!telefonoValido) {
                    botones.mostrarMensaje(Register.this, 
                        "El número de teléfono debe contener exactamente 10 dígitos numéricos.", 
                        "Teléfono inválido", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Usuario nuevo = new Usuario(1, user, email, pass, name, num);
                UsuarioSQL consulta = new UsuarioSQL();
                consulta.registrarUsuario(nuevo);
                
                botones.mostrarMensaje(Register.this, 
                    "¡Registro exitoso! Bienvenido a la Taberna del Groschen, " + name + ".", 
                    "Nuevo Viajero", 
                    JOptionPane.INFORMATION_MESSAGE);
                    
                new Login().setVisible(true);
                Register.this.dispose();
            }
        });
        
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Login().setVisible(true);
                Register.this.dispose();
            }
        });
    }
}