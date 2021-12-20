package Vista;

import Modelo.ClienteTo;
import Dao.ClienteDao;
import Modelo.ComboTo;
import Modelo.ConfigTo;
import Modelo.DetalleTo;
import Eventos.Eventos;
import Dao.LoginDao;
import Modelo.ProductosTo;
import Dao.ProductosDao;
import Modelo.ProveedorTo;
import Dao.ProveedorDao;
import Modelo.VentaTo;
import Dao.VentaDao;
import Conexion.Conexion;
import Modelo.LoginTo;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public final class Sistema extends javax.swing.JFrame {

    Date fechaVenta = new Date();
    String fechaActual = new SimpleDateFormat("dd/MM/yyyy").format(fechaVenta);
    ClienteTo cl = new ClienteTo();
    ClienteDao client = new ClienteDao();
    ProveedorTo pr = new ProveedorTo();
    ProveedorDao PrDao = new ProveedorDao();
    ProductosTo pro = new ProductosTo();
    ProductosDao proDao = new ProductosDao();
    VentaTo v = new VentaTo();
    VentaDao Vdao = new VentaDao();
    DetalleTo Dv = new DetalleTo();
    ConfigTo conf = new ConfigTo();
    Eventos event = new Eventos();
    LoginTo lg = new LoginTo();
    LoginDao login = new LoginDao();
    DefaultTableModel modelo = new DefaultTableModel();
    DefaultTableModel tmp = new DefaultTableModel();
    ButtonGroup tipo = new ButtonGroup();
    int item;
    double Totalpagar = 0.00;

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    int r;

    public Sistema() {
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
    }

    public Sistema(LoginTo priv) {
        initComponents();
        this.setLocationRelativeTo(null);
        txtIdCliente.setVisible(false);
        txtIdVenta.setVisible(false);
        txtIdPro.setVisible(false);
        txtIdproducto.setVisible(false);
        txtIdProveedor.setVisible(false);
        txtIdConfig.setVisible(false);
        txtIdCV.setVisible(false);
        ListarConfig();
        if (priv.getRol().equals("Asistente")) {
            btnProveedor.setEnabled(false);
            btnUsuarios.setEnabled(false);
            btnConfig.setEnabled(false);
            LabelVendedor.setText(priv.getNombre());
        } else {
            LabelVendedor.setText(priv.getNombre());
        }
        tipo.add(v_contado);
        tipo.add(v_credito);
        modificarTabla(TableVenta);
        modificarTabla(TableVentas);
        modificarTabla(TableVentaDetallada);
        modificarTabla(TableUsuarios);
        modificarTabla(TableProducto);
        modificarTabla(TableCliente);
        modificarTabla(TableProveedor);
        setTitle("Sistema de Ventas");
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/carro.png")).getImage());
    }

    public void ListarCliente() {
        List<ClienteTo> ListarCl = client.ListarCliente();
        modelo = (DefaultTableModel) TableCliente.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < ListarCl.size(); i++) {
            ob[0] = ListarCl.get(i).getId();
            ob[1] = ListarCl.get(i).getDni();
            ob[2] = ListarCl.get(i).getNombre();
            ob[3] = ListarCl.get(i).getTelefono();
            ob[4] = ListarCl.get(i).getDireccion();
            modelo.addRow(ob);
        }
        TableCliente.setModel(modelo);

    }

    public void ListarProveedor() {
        List<ProveedorTo> ListarPr = PrDao.ListarProveedor();
        modelo = (DefaultTableModel) TableProveedor.getModel();
        Object[] ob = new Object[5];
        for (int i = 0; i < ListarPr.size(); i++) {
            ob[0] = ListarPr.get(i).getId();
            ob[1] = ListarPr.get(i).getRuc();
            ob[2] = ListarPr.get(i).getNombre();
            ob[3] = ListarPr.get(i).getTelefono();
            ob[4] = ListarPr.get(i).getDireccion();
            modelo.addRow(ob);
        }
        TableProveedor.setModel(modelo);

    }

    public void ListarUsuarios() {
        List<LoginTo> Listar = login.ListarUsuarios();
        modelo = (DefaultTableModel) TableUsuarios.getModel();
        Object[] ob = new Object[4];
        for (int i = 0; i < Listar.size(); i++) {
            ob[0] = Listar.get(i).getId();
            ob[1] = Listar.get(i).getNombre();
            ob[2] = Listar.get(i).getUsuario();
            ob[3] = Listar.get(i).getRol();
            modelo.addRow(ob);
        }
        TableUsuarios.setModel(modelo);

    }

    public void ListarProductos() {
        List<ProductosTo> ListarPro = proDao.ListarProductos();
        modelo = (DefaultTableModel) TableProducto.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < ListarPro.size(); i++) {
            ob[0] = ListarPro.get(i).getId();
            ob[1] = ListarPro.get(i).getCodigo();
            ob[2] = ListarPro.get(i).getNombre();
            ob[3] = ListarPro.get(i).getProveedorPro();
            ob[4] = ListarPro.get(i).getStock();
            ob[5] = ListarPro.get(i).getPrecio();
            modelo.addRow(ob);
        }
        TableProducto.setModel(modelo);

    }

    public void ListarConfig() {
        conf = proDao.BuscarDatos();
        txtIdConfig.setText("" + conf.getId());
        txtRucConfig.setText("" + conf.getRuc());
        txtNombreConfig.setText("" + conf.getNombre());
        txtTelefonoConfig.setText("" + conf.getTelefono());
        txtDireccionConfig.setText("" + conf.getDireccion());
        txtMensaje.setText("" + conf.getMensaje());
    }

    public void ListarVentas() {
        List<VentaTo> ListarVenta = Vdao.Listarventas();
        modelo = (DefaultTableModel) TableVentas.getModel();
        Object[] ob = new Object[7];
        for (int i = 0; i < ListarVenta.size(); i++) {
            ob[0] = ListarVenta.get(i).getId();
            ob[1] = ListarVenta.get(i).getNombre_cli();
            ob[2] = ListarVenta.get(i).getVendedor();
            ob[3] = ListarVenta.get(i).getTotal();
            ob[4] = ListarVenta.get(i).getFecha();
            ob[5] = ListarVenta.get(i).getDebe();
            ob[6] = ListarVenta.get(i).getEstado();
            modelo.addRow(ob);
        }
        TableVentas.setModel(modelo);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        LabelVendedor = new javax.swing.JLabel();
        btnNuevaVenta = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        btnProveedor = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnVentas = new javax.swing.JButton();
        btnConfig = new javax.swing.JButton();
        btnUsuarios = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCodigoVenta = new javax.swing.JTextField();
        txtDescripcionVenta = new javax.swing.JTextField();
        txtCantidadVenta = new javax.swing.JTextField();
        txtPrecioVenta = new javax.swing.JTextField();
        txtStockDisponible = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableVenta = new javax.swing.JTable();
        btnEliminarventa = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtDniVenta = new javax.swing.JTextField();
        txtNombreClienteventa = new javax.swing.JTextField();
        btnGenerarVenta = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        LabelTotal = new javax.swing.JLabel();
        txtIdCV = new javax.swing.JTextField();
        txtIdPro = new javax.swing.JTextField();
        v_contado = new javax.swing.JRadioButton();
        v_credito = new javax.swing.JRadioButton();
        LabelPago = new javax.swing.JLabel();
        txtEntregado = new javax.swing.JTextField();
        LabelDebe = new javax.swing.JLabel();
        txtDebe = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtDniCliente = new javax.swing.JTextField();
        txtNombreCliente = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtTelefonoCliente = new javax.swing.JTextField();
        txtDirecionCliente = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        btnGuardarCliente = new javax.swing.JButton();
        btnEditarCliente = new javax.swing.JButton();
        btnNuevoCliente = new javax.swing.JButton();
        btnEliminarCliente = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableCliente = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtIdCliente = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableProveedor = new javax.swing.JTable();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        txtTelefonoProveedor = new javax.swing.JTextField();
        txtNombreproveedor = new javax.swing.JTextField();
        txtRucProveedor = new javax.swing.JTextField();
        txtDireccionProveedor = new javax.swing.JTextField();
        btnguardarProveedor = new javax.swing.JButton();
        btnEditarProveedor = new javax.swing.JButton();
        btnNuevoProveedor = new javax.swing.JButton();
        btnEliminarProveedor = new javax.swing.JButton();
        txtIdProveedor = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableProducto = new javax.swing.JTable();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        txtCodigoPro = new javax.swing.JTextField();
        txtDesPro = new javax.swing.JTextField();
        txtCantPro = new javax.swing.JTextField();
        txtPrecioPro = new javax.swing.JTextField();
        cbxProveedorPro = new javax.swing.JComboBox<>();
        btnGuardarpro = new javax.swing.JButton();
        btnEditarpro = new javax.swing.JButton();
        btnEliminarPro = new javax.swing.JButton();
        btnNuevoPro = new javax.swing.JButton();
        txtIdproducto = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TableVentas = new javax.swing.JTable();
        btnVentaDetallada = new javax.swing.JButton();
        txtIdVenta = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        txtFechaVenta = new javax.swing.JLabel();
        btnPagar = new javax.swing.JButton();
        txtBuscarVenta = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        LabelPago1 = new javax.swing.JLabel();
        txtPago = new javax.swing.JTextField();
        LabelDebe1 = new javax.swing.JLabel();
        txtSaldo = new javax.swing.JTextField();
        LabelDebe2 = new javax.swing.JLabel();
        txtEstado = new javax.swing.JTextField();
        btnActualizar = new javax.swing.JButton();
        txtdebe = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btnActualizarConfig = new javax.swing.JButton();
        txtMensaje = new javax.swing.JTextField();
        txtTelefonoConfig = new javax.swing.JTextField();
        txtDireccionConfig = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtRucConfig = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtNombreConfig = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtIdConfig = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        TableUsuarios = new javax.swing.JTable();
        jLabel34 = new javax.swing.JLabel();
        btnGuardarUsu = new javax.swing.JButton();
        btnEliminarUsu = new javax.swing.JButton();
        btnNuevoUsu = new javax.swing.JButton();
        txtUsuario = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        jLabel36 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        cbxRol = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        txtIdusuario = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        TableVentaDetallada = new javax.swing.JTable();
        jLabel58 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        d_direccion = new javax.swing.JLabel();
        d_total = new javax.swing.JLabel();
        d_cliente = new javax.swing.JLabel();
        d_telefono = new javax.swing.JLabel();
        d_dni = new javax.swing.JLabel();
        d_venta = new javax.swing.JLabel();
        d_fechaventa = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 196, 235, 27));

        LabelVendedor.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        LabelVendedor.setForeground(new java.awt.Color(255, 255, 255));
        LabelVendedor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelVendedor.setText("Sistema de Venta");
        LabelVendedor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(LabelVendedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 223, 230, 50));

        btnNuevaVenta.setBackground(new java.awt.Color(255, 255, 255));
        btnNuevaVenta.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        btnNuevaVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Nventa.png"))); // NOI18N
        btnNuevaVenta.setText("Nueva Venta");
        btnNuevaVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNuevaVenta.setFocusable(false);
        btnNuevaVenta.setName(""); // NOI18N
        btnNuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaVentaActionPerformed(evt);
            }
        });
        getContentPane().add(btnNuevaVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 210, 60));

        btnClientes.setBackground(new java.awt.Color(255, 255, 255));
        btnClientes.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Clientes.png"))); // NOI18N
        btnClientes.setText("Clientes");
        btnClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnClientes.setFocusable(false);
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });
        getContentPane().add(btnClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 210, 60));

        btnProveedor.setBackground(new java.awt.Color(255, 255, 255));
        btnProveedor.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        btnProveedor.setForeground(new java.awt.Color(51, 51, 51));
        btnProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/proveedor.png"))); // NOI18N
        btnProveedor.setText("Proveedor");
        btnProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnProveedor.setFocusable(false);
        btnProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedorActionPerformed(evt);
            }
        });
        getContentPane().add(btnProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 210, 60));

        btnProductos.setBackground(new java.awt.Color(255, 255, 255));
        btnProductos.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        btnProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/producto.png"))); // NOI18N
        btnProductos.setText("Productos");
        btnProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnProductos.setFocusable(false);
        btnProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductosMouseClicked(evt);
            }
        });
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });
        getContentPane().add(btnProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 530, 210, 60));

        btnVentas.setBackground(new java.awt.Color(255, 255, 255));
        btnVentas.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        btnVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/compras.png"))); // NOI18N
        btnVentas.setText("Ventas");
        btnVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVentas.setFocusable(false);
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });
        getContentPane().add(btnVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(1145, 290, 210, 60));

        btnConfig.setBackground(new java.awt.Color(255, 255, 255));
        btnConfig.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        btnConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/config.png"))); // NOI18N
        btnConfig.setText("Config");
        btnConfig.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnConfig.setFocusable(false);
        btnConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigActionPerformed(evt);
            }
        });
        getContentPane().add(btnConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(1145, 369, 210, 60));

        btnUsuarios.setBackground(new java.awt.Color(255, 255, 255));
        btnUsuarios.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        btnUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/proveedor.png"))); // NOI18N
        btnUsuarios.setText("Usuarios");
        btnUsuarios.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnUsuarios.setFocusable(false);
        btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosActionPerformed(evt);
            }
        });
        getContentPane().add(btnUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(1145, 450, 210, 60));

        btnCerrar.setBackground(new java.awt.Color(255, 255, 255));
        btnCerrar.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir_1.png"))); // NOI18N
        btnCerrar.setText("Cerrar Sesión");
        btnCerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCerrar.setFocusable(false);
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1145, 530, 210, 60));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 204));
        jLabel3.setText("Código");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        jLabel4.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 204));
        jLabel4.setText("Descripción");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, -1, -1));

        jLabel5.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 204));
        jLabel5.setText("Cant");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 30, -1, -1));

        jLabel6.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 204));
        jLabel6.setText("Precio");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 30, -1, -1));

        jLabel7.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 204));
        jLabel7.setText("Stock Disponible");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 30, -1, -1));

        txtCodigoVenta.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        txtCodigoVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoVentaKeyTyped(evt);
            }
        });
        jPanel2.add(txtCodigoVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 130, 33));

        txtDescripcionVenta.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        txtDescripcionVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionVentaKeyTyped(evt);
            }
        });
        jPanel2.add(txtDescripcionVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 70, 250, 33));

        txtCantidadVenta.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        txtCantidadVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadVentaKeyTyped(evt);
            }
        });
        jPanel2.add(txtCantidadVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 70, 50, 33));

        txtPrecioVenta.setEditable(false);
        txtPrecioVenta.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jPanel2.add(txtPrecioVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 70, 80, 33));

        txtStockDisponible.setEditable(false);
        txtStockDisponible.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jPanel2.add(txtStockDisponible, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 70, 79, 33));

        TableVenta.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
        TableVenta.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        TableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DESCRIPCIÓN", "CANTIDAD", "PRECIO U.", "PRECIO TOTAL"
            }
        ));
        jScrollPane1.setViewportView(TableVenta);
        if (TableVenta.getColumnModel().getColumnCount() > 0) {
            TableVenta.getColumnModel().getColumn(0).setMinWidth(60);
            TableVenta.getColumnModel().getColumn(0).setMaxWidth(60);
            TableVenta.getColumnModel().getColumn(1).setMinWidth(300);
            TableVenta.getColumnModel().getColumn(1).setMaxWidth(300);
        }

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 131, 843, 150));

        btnEliminarventa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/boton_eliminar.png"))); // NOI18N
        btnEliminarventa.setFocusable(false);
        btnEliminarventa.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/boton_eliminar.png"))); // NOI18N
        btnEliminarventa.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar_press.png"))); // NOI18N
        btnEliminarventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarventaActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminarventa, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 67, 60, 40));

        jLabel8.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 204));
        jLabel8.setText("Dni");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, -1));

        jLabel9.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 204));
        jLabel9.setText("Nombre:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 300, -1, -1));

        txtDniVenta.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        txtDniVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDniVentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniVentaKeyTyped(evt);
            }
        });
        jPanel2.add(txtDniVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 116, 33));

        txtNombreClienteventa.setEditable(false);
        txtNombreClienteventa.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jPanel2.add(txtNombreClienteventa, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 340, 310, 33));

        btnGenerarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buy.png"))); // NOI18N
        btnGenerarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVentaActionPerformed(evt);
            }
        });
        jPanel2.add(btnGenerarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 400, 70, 45));

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/money.png"))); // NOI18N
        jLabel10.setText("Total a Pagar:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 340, -1, -1));

        LabelTotal.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        LabelTotal.setText("-----");
        jPanel2.add(LabelTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 340, -1, -1));

        txtIdCV.setForeground(new java.awt.Color(255, 255, 255));
        txtIdCV.setCaretColor(new java.awt.Color(255, 255, 255));
        txtIdCV.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        jPanel2.add(txtIdCV, new org.netbeans.lib.awtextra.AbsoluteConstraints(327, 395, 0, 0));
        jPanel2.add(txtIdPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(678, 146, -1, 0));

        v_contado.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        v_contado.setText("Contado");
        v_contado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                v_contadoActionPerformed(evt);
            }
        });
        jPanel2.add(v_contado, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, -1, -1));

        v_credito.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        v_credito.setText("Credito");
        v_credito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                v_creditoActionPerformed(evt);
            }
        });
        jPanel2.add(v_credito, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 410, 90, -1));

        LabelPago.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        LabelPago.setForeground(new java.awt.Color(0, 51, 204));
        LabelPago.setText("Pago:");
        jPanel2.add(LabelPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 410, -1, -1));

        txtEntregado.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        txtEntregado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEntregadoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEntregadoKeyTyped(evt);
            }
        });
        jPanel2.add(txtEntregado, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 410, 150, -1));

        LabelDebe.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        LabelDebe.setForeground(new java.awt.Color(0, 51, 204));
        LabelDebe.setText("Debe:");
        jPanel2.add(LabelDebe, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 410, -1, -1));

        txtDebe.setEditable(false);
        txtDebe.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        txtDebe.setText("0.0");
        txtDebe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDebeKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDebeKeyTyped(evt);
            }
        });
        jPanel2.add(txtDebe, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 410, 150, -1));

        jTabbedPane1.addTab("1", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Dni:");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 125, -1, -1));

        txtDniCliente.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtDniCliente.setBorder(null);
        jPanel3.add(txtDniCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 125, 158, 25));

        txtNombreCliente.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtNombreCliente.setBorder(null);
        jPanel3.add(txtNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 165, 158, 25));

        jLabel13.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Nombre:");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 165, -1, -1));

        jLabel14.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Télefono:");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 205, -1, -1));

        txtTelefonoCliente.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtTelefonoCliente.setBorder(null);
        jPanel3.add(txtTelefonoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 205, 158, 25));

        txtDirecionCliente.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtDirecionCliente.setBorder(null);
        jPanel3.add(txtDirecionCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 245, 158, 25));

        jLabel15.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Dirección:");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 245, -1, -1));

        btnGuardarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/GuardarTodo.png"))); // NOI18N
        btnGuardarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnGuardarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClienteActionPerformed(evt);
            }
        });
        jPanel3.add(btnGuardarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 300, 60, 40));

        btnEditarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Actualizar (2).png"))); // NOI18N
        btnEditarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteActionPerformed(evt);
            }
        });
        jPanel3.add(btnEditarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 363, 60, 40));

        btnNuevoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/nuevo.png"))); // NOI18N
        btnNuevoCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoClienteActionPerformed(evt);
            }
        });
        jPanel3.add(btnNuevoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 363, 60, 40));

        btnEliminarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        btnEliminarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClienteActionPerformed(evt);
            }
        });
        jPanel3.add(btnEliminarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 300, 60, 40));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cuadrito.png"))); // NOI18N
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 270, 360));

        TableCliente.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        TableCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DNI", "NOMBRE", "TELÉFONO", "DIRECCIÓN"
            }
        ));
        TableCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableClienteMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TableCliente);
        if (TableCliente.getColumnModel().getColumnCount() > 0) {
            TableCliente.getColumnModel().getColumn(0).setMinWidth(60);
            TableCliente.getColumnModel().getColumn(0).setMaxWidth(60);
            TableCliente.getColumnModel().getColumn(1).setMinWidth(100);
            TableCliente.getColumnModel().getColumn(1).setMaxWidth(100);
            TableCliente.getColumnModel().getColumn(2).setMinWidth(180);
            TableCliente.getColumnModel().getColumn(2).setMaxWidth(180);
            TableCliente.getColumnModel().getColumn(3).setMinWidth(100);
            TableCliente.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, 600, 360));

        jLabel2.setFont(new java.awt.Font("Gill Sans Ultra Bold", 0, 48)); // NOI18N
        jLabel2.setText("TABLA CLIENTES");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, -1, 40));
        jPanel3.add(txtIdCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 0, 0));

        jTabbedPane1.addTab("2", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableProveedor.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        TableProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "RUC", "NOMBRE", "TELÉFONO", "DIRECCIÓN"
            }
        ));
        TableProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProveedorMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TableProveedor);
        if (TableProveedor.getColumnModel().getColumnCount() > 0) {
            TableProveedor.getColumnModel().getColumn(0).setMinWidth(70);
            TableProveedor.getColumnModel().getColumn(0).setMaxWidth(70);
        }

        jPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, 600, 360));

        jLabel39.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Ruc:");
        jPanel4.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 125, -1, -1));

        jLabel40.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Nombre:");
        jPanel4.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 165, -1, -1));

        jLabel41.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Télefono:");
        jPanel4.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 205, -1, -1));

        jLabel42.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Dirección:");
        jPanel4.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 245, -1, -1));

        txtTelefonoProveedor.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtTelefonoProveedor.setBorder(null);
        jPanel4.add(txtTelefonoProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 205, 158, 25));

        txtNombreproveedor.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtNombreproveedor.setBorder(null);
        jPanel4.add(txtNombreproveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 165, 158, 25));

        txtRucProveedor.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtRucProveedor.setBorder(null);
        jPanel4.add(txtRucProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 125, 158, 25));

        txtDireccionProveedor.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtDireccionProveedor.setBorder(null);
        jPanel4.add(txtDireccionProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 245, 158, 25));

        btnguardarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/GuardarTodo.png"))); // NOI18N
        btnguardarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarProveedorActionPerformed(evt);
            }
        });
        jPanel4.add(btnguardarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 300, 60, 40));

        btnEditarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Actualizar (2).png"))); // NOI18N
        btnEditarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProveedorActionPerformed(evt);
            }
        });
        jPanel4.add(btnEditarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 363, 60, 40));

        btnNuevoProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/nuevo.png"))); // NOI18N
        btnNuevoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProveedorActionPerformed(evt);
            }
        });
        jPanel4.add(btnNuevoProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 363, 60, 40));

        btnEliminarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        btnEliminarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProveedorActionPerformed(evt);
            }
        });
        jPanel4.add(btnEliminarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 300, 60, 40));
        jPanel4.add(txtIdProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 100, -1, 0));

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cuadrito.png"))); // NOI18N
        jPanel4.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 270, 360));

        jLabel43.setFont(new java.awt.Font("Gill Sans Ultra Bold", 0, 48)); // NOI18N
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("TABLA PROVEEDOR");
        jLabel43.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel4.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 20, 900, 40));

        jTabbedPane1.addTab("3", jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableProducto.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        TableProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CODIGO", "DESCRIPCIÓN", "PROVEEDOR", "STOCK", "PRECIO"
            }
        ));
        TableProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableProductoMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TableProducto);
        if (TableProducto.getColumnModel().getColumnCount() > 0) {
            TableProducto.getColumnModel().getColumn(0).setMinWidth(60);
            TableProducto.getColumnModel().getColumn(0).setMaxWidth(60);
            TableProducto.getColumnModel().getColumn(1).setMinWidth(90);
            TableProducto.getColumnModel().getColumn(1).setMaxWidth(90);
            TableProducto.getColumnModel().getColumn(4).setMinWidth(70);
            TableProducto.getColumnModel().getColumn(4).setMaxWidth(70);
            TableProducto.getColumnModel().getColumn(5).setMinWidth(70);
            TableProducto.getColumnModel().getColumn(5).setMaxWidth(70);
        }

        jPanel5.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, 600, 360));

        jLabel45.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("Precio:");
        jPanel5.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 245, 60, -1));

        jLabel46.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Cantidad:");
        jPanel5.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 205, -1, -1));

        jLabel47.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setText("Descripcion:");
        jPanel5.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 165, -1, -1));

        jLabel48.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Proveedor:");
        jPanel5.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 285, -1, -1));

        jLabel49.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("Codigo:");
        jPanel5.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 125, -1, -1));

        txtCodigoPro.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtCodigoPro.setBorder(null);
        jPanel5.add(txtCodigoPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 125, 145, 25));

        txtDesPro.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtDesPro.setBorder(null);
        jPanel5.add(txtDesPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 165, 145, 25));

        txtCantPro.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtCantPro.setBorder(null);
        jPanel5.add(txtCantPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 205, 145, 25));

        txtPrecioPro.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtPrecioPro.setBorder(null);
        jPanel5.add(txtPrecioPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 245, 145, 25));

        cbxProveedorPro.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        cbxProveedorPro.setBorder(null);
        jPanel5.add(cbxProveedorPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 285, 145, 25));

        btnGuardarpro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/GuardarTodo.png"))); // NOI18N
        btnGuardarpro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarproActionPerformed(evt);
            }
        });
        jPanel5.add(btnGuardarpro, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 330, 60, 40));

        btnEditarpro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Actualizar (2).png"))); // NOI18N
        btnEditarpro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarproActionPerformed(evt);
            }
        });
        jPanel5.add(btnEditarpro, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 380, 60, 40));

        btnEliminarPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        btnEliminarPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProActionPerformed(evt);
            }
        });
        jPanel5.add(btnEliminarPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 330, 60, 40));

        btnNuevoPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/nuevo.png"))); // NOI18N
        btnNuevoPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProActionPerformed(evt);
            }
        });
        jPanel5.add(btnNuevoPro, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 380, 60, 40));
        jPanel5.add(txtIdproducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, -1, 0));

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cuadrito.png"))); // NOI18N
        jPanel5.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 270, 360));

        jLabel50.setFont(new java.awt.Font("Gill Sans Ultra Bold", 0, 48)); // NOI18N
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("TABLA PRODUCTOS");
        jLabel50.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel5.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 20, 900, 40));

        jTabbedPane1.addTab("4", jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableVentas.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        TableVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CLIENTE", "VENDEDOR", "TOTAL", "FECHA", "DEBE", "ESTADO"
            }
        ));
        TableVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableVentasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(TableVentas);
        if (TableVentas.getColumnModel().getColumnCount() > 0) {
            TableVentas.getColumnModel().getColumn(0).setMinWidth(70);
            TableVentas.getColumnModel().getColumn(0).setMaxWidth(70);
            TableVentas.getColumnModel().getColumn(3).setMinWidth(140);
            TableVentas.getColumnModel().getColumn(3).setMaxWidth(140);
            TableVentas.getColumnModel().getColumn(4).setMinWidth(120);
            TableVentas.getColumnModel().getColumn(4).setMaxWidth(120);
            TableVentas.getColumnModel().getColumn(5).setMinWidth(100);
            TableVentas.getColumnModel().getColumn(5).setMaxWidth(100);
            TableVentas.getColumnModel().getColumn(6).setMinWidth(110);
            TableVentas.getColumnModel().getColumn(6).setMaxWidth(110);
        }

        jPanel6.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 880, 250));

        btnVentaDetallada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/reporte.png"))); // NOI18N
        btnVentaDetallada.setBorderPainted(false);
        btnVentaDetallada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentaDetalladaActionPerformed(evt);
            }
        });
        jPanel6.add(btnVentaDetallada, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 50, 40));
        jPanel6.add(txtIdVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, -1, 0));

        jLabel51.setFont(new java.awt.Font("Gill Sans Ultra Bold", 0, 54)); // NOI18N
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("REPORTE DE VENTAS");
        jLabel51.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel6.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 900, 50));

        txtFechaVenta.setForeground(new java.awt.Color(255, 255, 255));
        jPanel6.add(txtFechaVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, -1, 10));

        btnPagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pago.png"))); // NOI18N
        btnPagar.setBorderPainted(false);
        btnPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagarActionPerformed(evt);
            }
        });
        jPanel6.add(btnPagar, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 70, 60, 50));

        txtBuscarVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarVentaKeyReleased(evt);
            }
        });
        jPanel6.add(txtBuscarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 92, 190, 30));

        jLabel33.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jLabel33.setText("Buscar");
        jPanel6.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 95, -1, -1));

        LabelPago1.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        LabelPago1.setForeground(new java.awt.Color(0, 51, 204));
        LabelPago1.setText("Pago:");
        jPanel6.add(LabelPago1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, -1, -1));

        txtPago.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        txtPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPagoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPagoKeyTyped(evt);
            }
        });
        jPanel6.add(txtPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 410, 150, -1));

        LabelDebe1.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        LabelDebe1.setForeground(new java.awt.Color(0, 51, 204));
        LabelDebe1.setText("Estado:");
        jPanel6.add(LabelDebe1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 410, -1, -1));

        txtSaldo.setEditable(false);
        txtSaldo.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        txtSaldo.setText("0.0");
        txtSaldo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSaldoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSaldoKeyTyped(evt);
            }
        });
        jPanel6.add(txtSaldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 410, 150, -1));

        LabelDebe2.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        LabelDebe2.setForeground(new java.awt.Color(0, 51, 204));
        LabelDebe2.setText("Debe:");
        jPanel6.add(LabelDebe2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 410, -1, -1));

        txtEstado.setEditable(false);
        txtEstado.setFont(new java.awt.Font("Century Gothic", 0, 20)); // NOI18N
        jPanel6.add(txtEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 410, 130, 30));

        btnActualizar.setBackground(new java.awt.Color(0, 0, 0));
        btnActualizar.setFont(new java.awt.Font("Impact", 0, 18)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel6.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 410, 140, 30));
        jPanel6.add(txtdebe, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 100, -1, -1));

        jTabbedPane1.addTab("5", jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnActualizarConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Actualizar (2).png"))); // NOI18N
        btnActualizarConfig.setText("ACTUALIZAR");
        btnActualizarConfig.setBorder(null);
        btnActualizarConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarConfigActionPerformed(evt);
            }
        });
        jPanel7.add(btnActualizarConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 370, 130, 40));

        txtMensaje.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtMensaje.setText(" ");
        txtMensaje.setBorder(null);
        jPanel7.add(txtMensaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 400, 30));

        txtTelefonoConfig.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtTelefonoConfig.setText(" ");
        txtTelefonoConfig.setBorder(null);
        jPanel7.add(txtTelefonoConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 250, 218, 30));

        txtDireccionConfig.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtDireccionConfig.setText(" ");
        txtDireccionConfig.setBorder(null);
        jPanel7.add(txtDireccionConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 160, 30));

        jLabel31.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("MENSAJE");
        jPanel7.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, -1, -1));

        jLabel30.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("DIRECCIÓN");
        jPanel7.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, -1));

        txtRucConfig.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtRucConfig.setText(" ");
        txtRucConfig.setBorder(null);
        jPanel7.add(txtRucConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 160, 30));

        jLabel27.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("RUC");
        jPanel7.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        jLabel28.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("NOMBRE");
        jPanel7.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, -1, -1));

        txtNombreConfig.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtNombreConfig.setText(" ");
        txtNombreConfig.setBorder(null);
        jPanel7.add(txtNombreConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, 220, 30));

        jLabel29.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("TELÉFONO");
        jPanel7.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, -1, -1));
        jPanel7.add(txtIdConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 0, 0));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/empresa.png"))); // NOI18N
        jPanel7.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 150, 430, 280));

        jLabel52.setFont(new java.awt.Font("Gill Sans Ultra Bold", 0, 58)); // NOI18N
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("DATOS DE LA EMPRESA");
        jLabel52.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel7.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 900, 50));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/2Cuadro.png"))); // NOI18N
        jPanel7.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 440, 310));

        jTabbedPane1.addTab("6", jPanel7);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableUsuarios.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        TableUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "USUARIO", "ROL"
            }
        ));
        TableUsuarios.setRowHeight(20);
        TableUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableUsuariosMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(TableUsuarios);
        if (TableUsuarios.getColumnModel().getColumnCount() > 0) {
            TableUsuarios.getColumnModel().getColumn(0).setMinWidth(80);
            TableUsuarios.getColumnModel().getColumn(0).setMaxWidth(80);
            TableUsuarios.getColumnModel().getColumn(2).setMinWidth(150);
            TableUsuarios.getColumnModel().getColumn(2).setMaxWidth(150);
            TableUsuarios.getColumnModel().getColumn(3).setMinWidth(130);
            TableUsuarios.getColumnModel().getColumn(3).setMaxWidth(130);
        }

        jPanel8.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, 600, 360));

        jLabel34.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("Usuario:");
        jPanel8.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 105, -1, -1));

        btnGuardarUsu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/GuardarTodo.png"))); // NOI18N
        btnGuardarUsu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarUsuActionPerformed(evt);
            }
        });
        jPanel8.add(btnGuardarUsu, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 60, 40));

        btnEliminarUsu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        btnEliminarUsu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarUsuActionPerformed(evt);
            }
        });
        jPanel8.add(btnEliminarUsu, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 380, 60, 40));

        btnNuevoUsu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/nuevo.png"))); // NOI18N
        btnNuevoUsu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoUsuActionPerformed(evt);
            }
        });
        jPanel8.add(btnNuevoUsu, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 380, 60, 40));

        txtUsuario.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtUsuario.setBorder(null);
        jPanel8.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 135, 230, 25));

        jLabel35.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("Password:");
        jPanel8.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 175, -1, -1));

        txtPass.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        txtPass.setBorder(null);
        jPanel8.add(txtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 230, 25));

        jLabel36.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Nombre:");
        jPanel8.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 245, -1, -1));

        txtNombre.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        txtNombre.setBorder(null);
        jPanel8.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 230, 25));

        jLabel37.setFont(new java.awt.Font("Impact", 0, 20)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Rol:");
        jPanel8.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 315, -1, -1));

        cbxRol.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        cbxRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Asistente" }));
        cbxRol.setBorder(null);
        jPanel8.add(cbxRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 345, 230, 25));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Cuadrito.png"))); // NOI18N
        jPanel8.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 270, 360));

        jLabel53.setFont(new java.awt.Font("Gill Sans Ultra Bold", 0, 48)); // NOI18N
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setText("TABLA USUARIOS");
        jLabel53.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel8.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(-7, 20, 910, 40));
        jPanel8.add(txtIdusuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 10, 0));

        jTabbedPane1.addTab("7", jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableVentaDetallada.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        TableVentaDetallada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CANTIDAD", "DESCRIPCION", "P.UNITARIO", "P.TOTAL"
            }
        ));
        TableVentaDetallada.setRowHeight(20);
        jScrollPane7.setViewportView(TableVentaDetallada);
        if (TableVentaDetallada.getColumnModel().getColumnCount() > 0) {
            TableVentaDetallada.getColumnModel().getColumn(0).setMinWidth(150);
            TableVentaDetallada.getColumnModel().getColumn(0).setMaxWidth(150);
            TableVentaDetallada.getColumnModel().getColumn(2).setMinWidth(150);
            TableVentaDetallada.getColumnModel().getColumn(2).setMaxWidth(150);
            TableVentaDetallada.getColumnModel().getColumn(3).setMinWidth(150);
            TableVentaDetallada.getColumnModel().getColumn(3).setMaxWidth(150);
        }

        jPanel9.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 880, 210));

        jLabel58.setFont(new java.awt.Font("Gill Sans Ultra Bold", 0, 50)); // NOI18N
        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel58.setText("VENTA DETALLADA");
        jLabel58.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel9.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 24, 610, 50));

        jLabel18.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 51, 204));
        jLabel18.setText("  DNI                      Nombre                                                                    Telefono               Dirección                                       ");
        jPanel9.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        d_direccion.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jPanel9.add(d_direccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 140, 260, 30));

        d_total.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jPanel9.add(d_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 420, 150, 30));

        d_cliente.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jPanel9.add(d_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 140, 330, 30));

        d_telefono.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jPanel9.add(d_telefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, 100, 30));

        d_dni.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jPanel9.add(d_dni, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 90, 30));

        d_venta.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jPanel9.add(d_venta, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 420, 110, 30));

        d_fechaventa.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jPanel9.add(d_fechaventa, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, 110, 30));

        jLabel19.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 51, 204));
        jLabel19.setText("Total");
        jPanel9.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 420, -1, -1));

        jLabel20.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 51, 204));
        jLabel20.setText("Fecha");
        jPanel9.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, -1, -1));

        jLabel22.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 51, 204));
        jLabel22.setText("Venta ");
        jPanel9.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 420, -1, -1));

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Bordeplomo.jpeg"))); // NOI18N
        jPanel9.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 880, 30));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Bordeplomo.jpeg"))); // NOI18N
        jPanel9.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 185, 880, 20));

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Bordeplomo.jpeg"))); // NOI18N
        jPanel9.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 395, 880, 10));

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Bordeplomo.jpeg"))); // NOI18N
        jPanel9.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 420, 80, 30));

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Bordeplomo.jpeg"))); // NOI18N
        jPanel9.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 80, 30));

        jTabbedPane1.addTab("8", jPanel9);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 202, 903, 500));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Sistema_color.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCodigoVenta.getText())) {
                if (pro.getNombre() != null) {
                    txtCantidadVenta.requestFocus();
                } else {
                    LimparVenta();
                    txtCodigoVenta.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el codigo del productos");
                LimparVenta();
                txtCodigoVenta.requestFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoVentaKeyPressed

    private void txtCodigoVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCodigoVentaKeyTyped

    private void txtDescripcionVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionVentaKeyTyped
        event.textKeyPress(evt);
    }//GEN-LAST:event_txtDescripcionVentaKeyTyped

    private void txtCantidadVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtCantidadVenta.getText())) {
                int id = Integer.parseInt(txtIdPro.getText());
                String descripcion = txtDescripcionVenta.getText();
                int cant = Integer.parseInt(txtCantidadVenta.getText());
                double precio = Double.parseDouble(txtPrecioVenta.getText());
                double total = cant * precio;
                int stock = Integer.parseInt(txtStockDisponible.getText());
                if (stock >= cant) {
                    item = item + 1;
                    tmp = (DefaultTableModel) TableVenta.getModel();
                    for (int i = 0; i < TableVenta.getRowCount(); i++) {
                        if (TableVenta.getValueAt(i, 1).equals(txtDescripcionVenta.getText())) {
                            JOptionPane.showMessageDialog(null, "El producto ya esta registrado");
                            return;
                        }
                    }
                    ArrayList lista = new ArrayList();
                    lista.add(item);
                    lista.add(id);
                    lista.add(descripcion);
                    lista.add(cant);
                    lista.add(precio);
                    lista.add(total);
                    Object[] O = new Object[5];
                    O[0] = lista.get(1);
                    O[1] = lista.get(2);
                    O[2] = lista.get(3);
                    O[3] = lista.get(4);
                    O[4] = lista.get(5);
                    tmp.addRow(O);
                    TableVenta.setModel(tmp);
                    TotalPagar();
                    LimparVenta();
                    txtCodigoVenta.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Stock no disponible");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese Cantidad");
            }
        }
    }//GEN-LAST:event_txtCantidadVentaKeyPressed

    private void txtCantidadVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadVentaKeyTyped
        // TODO add your handling code here:
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtCantidadVentaKeyTyped

    private void btnEliminarventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarventaActionPerformed
        // TODO add your handling code here:
        modelo = (DefaultTableModel) TableVenta.getModel();
        modelo.removeRow(TableVenta.getSelectedRow());
        TotalPagar();
        txtCodigoVenta.requestFocus();
    }//GEN-LAST:event_btnEliminarventaActionPerformed

    private void txtDniVentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniVentaKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtDniVenta.getText())) {
                int dni = Integer.parseInt(txtDniVenta.getText());
                cl = client.Buscarcliente(dni);
                if (cl.getNombre() != null) {
                    txtNombreClienteventa.setText("" + cl.getNombre());
                    txtIdCV.setText("" + cl.getId());
                } else {
                    txtDniVenta.setText("");
                    JOptionPane.showMessageDialog(null, "El cliente no existe");
                }
            }
        }
    }//GEN-LAST:event_txtDniVentaKeyPressed

    private void txtDniVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniVentaKeyTyped
        event.numberKeyPress(evt);
    }//GEN-LAST:event_txtDniVentaKeyTyped

    private void btnGenerarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarVentaActionPerformed
        // TODO add your handling code here:
        if (TableVenta.getRowCount() > 0) {
            if (!"".equals(txtNombreClienteventa.getText())) {
                int pregunta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de realizar la venta?");
                if (pregunta == 0) {
                    RegistrarVenta();
                    RegistrarDetalle();
                    ActualizarStock();
                    LimpiarTabla(TableVenta);
                    LimpiarClienteventa();
                    LabelTotal.setText("-------");
                    JOptionPane.showMessageDialog(null, "Se registro correctamente");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debes buscar un cliente");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay productos en la venta");
        }
    }//GEN-LAST:event_btnGenerarVentaActionPerformed

    private void TableClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableClienteMouseClicked
        // TODO add your handling code here:
        btnEditarCliente.setEnabled(true);
        btnEliminarCliente.setEnabled(true);
        btnGuardarCliente.setEnabled(false);
        int fila = TableCliente.rowAtPoint(evt.getPoint());
        txtIdCliente.setText(TableCliente.getValueAt(fila, 0).toString());
        txtDniCliente.setText(TableCliente.getValueAt(fila, 1).toString());
        txtNombreCliente.setText(TableCliente.getValueAt(fila, 2).toString());
        txtTelefonoCliente.setText(TableCliente.getValueAt(fila, 3).toString());
        txtDirecionCliente.setText(TableCliente.getValueAt(fila, 4).toString());
    }//GEN-LAST:event_TableClienteMouseClicked

    private void TableProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProveedorMouseClicked
        // TODO add your handling code here:
        btnEditarProveedor.setEnabled(true);
        btnEliminarProveedor.setEnabled(true);
        btnguardarProveedor.setEnabled(false);
        int fila = TableProveedor.rowAtPoint(evt.getPoint());
        txtIdProveedor.setText(TableProveedor.getValueAt(fila, 0).toString());
        txtRucProveedor.setText(TableProveedor.getValueAt(fila, 1).toString());
        txtNombreproveedor.setText(TableProveedor.getValueAt(fila, 2).toString());
        txtTelefonoProveedor.setText(TableProveedor.getValueAt(fila, 3).toString());
        txtDireccionProveedor.setText(TableProveedor.getValueAt(fila, 4).toString());
    }//GEN-LAST:event_TableProveedorMouseClicked

    private void TableProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableProductoMouseClicked
        // TODO add your handling code here:
        btnEditarpro.setEnabled(true);
        btnEliminarPro.setEnabled(true);
        btnGuardarpro.setEnabled(true);
        int fila = TableProducto.rowAtPoint(evt.getPoint());
        txtIdproducto.setText(TableProducto.getValueAt(fila, 0).toString());
        pro = proDao.BuscarId(Integer.parseInt(txtIdproducto.getText()));
        txtCodigoPro.setText(pro.getCodigo());
        txtDesPro.setText(pro.getNombre());
        txtCantPro.setText("" + pro.getStock());
        txtPrecioPro.setText("" + pro.getPrecio());
        cbxProveedorPro.setSelectedItem(new ComboTo(pro.getProveedor(), pro.getProveedorPro()));
    }//GEN-LAST:event_TableProductoMouseClicked

    private void TableVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableVentasMouseClicked
        // TODO add your handling code here:
        int fila = TableVentas.rowAtPoint(evt.getPoint());
        txtIdVenta.setText(TableVentas.getValueAt(fila, 0).toString());
        txtFechaVenta.setText(TableVentas.getValueAt(fila, 4).toString());
        txtSaldo.setText(TableVentas.getValueAt(fila, 5).toString());
        txtdebe.setText(TableVentas.getValueAt(fila, 5).toString());
        txtEstado.setText(TableVentas.getValueAt(fila, 6).toString());
    }//GEN-LAST:event_TableVentasMouseClicked

    private void btnVentaDetalladaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentaDetalladaActionPerformed
        if (txtIdVenta.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        } else {
            LimpiarTabla(TableVentaDetallada);
            LimpiarDetalle();
            v = Vdao.BuscarVenta(Integer.parseInt(txtIdVenta.getText()));
            ventaDt(v.getId(), v.getCliente(), v.getTotal(), txtFechaVenta.getText());
            jTabbedPane1.setSelectedIndex(7);
        }
    }//GEN-LAST:event_btnVentaDetalladaActionPerformed

    private void btnNuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaVentaActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_btnNuevaVentaActionPerformed

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        // TODO add your handling code here:
        LimpiarTabla(TableCliente);
        ListarCliente();
        btnEditarCliente.setEnabled(false);
        btnEliminarCliente.setEnabled(false);
        LimpiarCliente();
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_btnClientesActionPerformed

    private void btnProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedorActionPerformed
        // TODO add your handling code here:
        LimpiarTabla(TableProveedor);
        ListarProveedor();
        jTabbedPane1.setSelectedIndex(2);
        btnEditarProveedor.setEnabled(false);
        btnEliminarProveedor.setEnabled(false);
        LimpiarProveedor();
    }//GEN-LAST:event_btnProveedorActionPerformed

    private void btnProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductosMouseClicked
        // TODO add your handling code here:
        cbxProveedorPro.removeAllItems();
        llenarProveedor();

    }//GEN-LAST:event_btnProductosMouseClicked

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        // TODO add your handling code here:
        LimpiarTabla(TableProducto);
        ListarProductos();
        jTabbedPane1.setSelectedIndex(3);
        btnEditarpro.setEnabled(false);
        btnEliminarPro.setEnabled(false);
        btnGuardarpro.setEnabled(true);
        LimpiarProductos();
    }//GEN-LAST:event_btnProductosActionPerformed

    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(4);
        LimpiarTabla(TableVentas);
        ListarVentas();
    }//GEN-LAST:event_btnVentasActionPerformed

    private void btnConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(5);
        ListarConfig();
    }//GEN-LAST:event_btnConfigActionPerformed

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        // TODO add your handling code here:
        jTabbedPane1.setSelectedIndex(6);
        LimpiarTabla(TableUsuarios);
        ListarUsuarios();
    }//GEN-LAST:event_btnUsuariosActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        Sistema sis = new Sistema();
        sis.setVisible(false);
        dispose();
        Login log = new Login();
        log.setVisible(true);
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnActualizarConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarConfigActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtRucConfig.getText()) || !"".equals(txtNombreConfig.getText()) || !"".equals(txtTelefonoConfig.getText()) || !"".equals(txtDireccionConfig.getText())) {
            conf.setRuc(txtRucConfig.getText());
            conf.setNombre(txtNombreConfig.getText());
            conf.setTelefono(txtTelefonoConfig.getText());
            conf.setDireccion(txtDireccionConfig.getText());
            conf.setMensaje(txtMensaje.getText());
            conf.setId(Integer.parseInt(txtIdConfig.getText()));
            proDao.ModificarDatos(conf);
            JOptionPane.showMessageDialog(null, "Datos de la empresa modificados");
            ListarConfig();
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnActualizarConfigActionPerformed

    private void btnGuardarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClienteActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtDniCliente.getText()) || !"".equals(txtNombreCliente.getText()) || !"".equals(txtTelefonoCliente.getText()) || !"".equals(txtDirecionCliente.getText())) {
            cl.setDni(txtDniCliente.getText());
            cl.setNombre(txtNombreCliente.getText());
            cl.setTelefono(txtTelefonoCliente.getText());
            cl.setDireccion(txtDirecionCliente.getText());
            client.RegistrarCliente(cl);
            JOptionPane.showMessageDialog(null, "Cliente Registrado");
            LimpiarTabla(TableCliente);
            LimpiarCliente();
            ListarCliente();
            btnEditarCliente.setEnabled(false);
            btnEliminarCliente.setEnabled(false);
            btnGuardarCliente.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnGuardarClienteActionPerformed

    private void btnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteActionPerformed
        // TODO add your handling code here:
        LimpiarCliente();
        btnEditarCliente.setEnabled(false);
        btnEliminarCliente.setEnabled(false);
        btnGuardarCliente.setEnabled(true);
    }//GEN-LAST:event_btnNuevoClienteActionPerformed

    private void btnguardarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarProveedorActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtRucProveedor.getText()) || !"".equals(txtNombreproveedor.getText()) || !"".equals(txtTelefonoProveedor.getText()) || !"".equals(txtDireccionProveedor.getText())) {
            pr.setRuc(txtRucProveedor.getText());
            pr.setNombre(txtNombreproveedor.getText());
            pr.setTelefono(txtTelefonoProveedor.getText());
            pr.setDireccion(txtDireccionProveedor.getText());
            PrDao.RegistrarProveedor(pr);
            JOptionPane.showMessageDialog(null, "Proveedor Registrado");
            LimpiarTabla(TableProveedor);
            ListarProveedor();
            LimpiarProveedor();
            btnEditarProveedor.setEnabled(false);
            btnEliminarProveedor.setEnabled(false);
            btnguardarProveedor.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estas vacios");
        }
    }//GEN-LAST:event_btnguardarProveedorActionPerformed

    private void btnNuevoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProveedorActionPerformed
        // TODO add your handling code here:
        LimpiarProveedor();
        btnEditarProveedor.setEnabled(false);
        btnEliminarProveedor.setEnabled(false);
        btnguardarProveedor.setEnabled(true);
    }//GEN-LAST:event_btnNuevoProveedorActionPerformed

    private void btnEliminarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProveedorActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtIdProveedor.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdProveedor.getText());
                PrDao.EliminarProveedor(id);
                LimpiarTabla(TableProveedor);
                ListarProveedor();
                LimpiarProveedor();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }//GEN-LAST:event_btnEliminarProveedorActionPerformed

    private void btnEditarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProveedorActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtIdProveedor.getText())) {
            JOptionPane.showMessageDialog(null, "Seleecione una fila");
        } else {
            if (!"".equals(txtRucProveedor.getText()) || !"".equals(txtNombreproveedor.getText()) || !"".equals(txtTelefonoProveedor.getText()) || !"".equals(txtDireccionProveedor.getText())) {
                pr.setRuc(txtRucProveedor.getText());
                pr.setNombre(txtNombreproveedor.getText());
                pr.setTelefono(txtTelefonoProveedor.getText());
                pr.setDireccion(txtDireccionProveedor.getText());
                pr.setId(Integer.parseInt(txtIdProveedor.getText()));
                PrDao.ModificarProveedor(pr);
                JOptionPane.showMessageDialog(null, "Proveedor Modificado");
                LimpiarTabla(TableProveedor);
                ListarProveedor();
                LimpiarProveedor();
                btnEditarProveedor.setEnabled(false);
                btnEliminarProveedor.setEnabled(false);
                btnguardarProveedor.setEnabled(true);
            }
        }
    }//GEN-LAST:event_btnEditarProveedorActionPerformed

    private void btnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClienteActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtIdCliente.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdCliente.getText());
                client.EliminarCliente(id);
                LimpiarTabla(TableCliente);
                LimpiarCliente();
                ListarCliente();
            }
        }
    }//GEN-LAST:event_btnEliminarClienteActionPerformed

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtIdCliente.getText())) {
            JOptionPane.showMessageDialog(null, "seleccione una fila");
        } else {

            if (!"".equals(txtDniCliente.getText()) || !"".equals(txtNombreCliente.getText()) || !"".equals(txtTelefonoCliente.getText())) {
                cl.setDni(txtDniCliente.getText());
                cl.setNombre(txtNombreCliente.getText());
                cl.setTelefono(txtTelefonoCliente.getText());
                cl.setDireccion(txtDirecionCliente.getText());
                cl.setId(Integer.parseInt(txtIdCliente.getText()));
                client.ModificarCliente(cl);
                JOptionPane.showMessageDialog(null, "Cliente Modificado");
                LimpiarTabla(TableCliente);
                LimpiarCliente();
                ListarCliente();
            } else {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            }
        }
    }//GEN-LAST:event_btnEditarClienteActionPerformed

    private void btnGuardarproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarproActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtCodigoPro.getText()) || !"".equals(txtDesPro.getText()) || !"".equals(cbxProveedorPro.getSelectedItem()) || !"".equals(txtCantPro.getText()) || !"".equals(txtPrecioPro.getText())) {
            pro.setCodigo(txtCodigoPro.getText());
            pro.setNombre(txtDesPro.getText());
            ComboTo itemP = (ComboTo) cbxProveedorPro.getSelectedItem();
            pro.setProveedor(itemP.getId());
            pro.setStock(Integer.parseInt(txtCantPro.getText()));
            pro.setPrecio(Double.parseDouble(txtPrecioPro.getText()));
            proDao.RegistrarProductos(pro);
            JOptionPane.showMessageDialog(null, "Producto Registrado");
            LimpiarTabla(TableProducto);
            ListarProductos();
            LimpiarProductos();
            cbxProveedorPro.removeAllItems();
            llenarProveedor();
            btnEditarpro.setEnabled(false);
            btnEliminarPro.setEnabled(false);
            btnGuardarpro.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");
        }
    }//GEN-LAST:event_btnGuardarproActionPerformed

    private void btnEliminarProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtIdproducto.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdproducto.getText());
                proDao.EliminarProductos(id);
                LimpiarTabla(TableProducto);
                LimpiarProductos();
                ListarProductos();
                btnEditarpro.setEnabled(false);
                btnEliminarPro.setEnabled(false);
                btnGuardarpro.setEnabled(true);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        }
    }//GEN-LAST:event_btnEliminarProActionPerformed

    private void btnEditarproActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarproActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtIdproducto.getText())) {
            JOptionPane.showMessageDialog(null, "Seleecione una fila");
        } else {
            if (!"".equals(txtCodigoPro.getText()) || !"".equals(txtDesPro.getText()) || !"".equals(txtCantPro.getText()) || !"".equals(txtPrecioPro.getText())) {
                pro.setCodigo(txtCodigoPro.getText());
                pro.setNombre(txtDesPro.getText());
                ComboTo itemP = (ComboTo) cbxProveedorPro.getSelectedItem();
                pro.setProveedor(itemP.getId());
                pro.setStock(Integer.parseInt(txtCantPro.getText()));
                pro.setPrecio(Double.parseDouble(txtPrecioPro.getText()));
                pro.setId(Integer.parseInt(txtIdproducto.getText()));
                proDao.ModificarProductos(pro);
                JOptionPane.showMessageDialog(null, "Producto Modificado");
                LimpiarTabla(TableProducto);
                ListarProductos();
                LimpiarProductos();
                cbxProveedorPro.removeAllItems();
                llenarProveedor();
                btnEditarpro.setEnabled(false);
                btnEliminarPro.setEnabled(false);
                btnGuardarpro.setEnabled(true);
            }
        }
    }//GEN-LAST:event_btnEditarproActionPerformed

    private void btnNuevoProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProActionPerformed
        // TODO add your handling code here:
        LimpiarProductos();
        btnEditarpro.setEnabled(false);
        btnEliminarPro.setEnabled(false);
        btnGuardarpro.setEnabled(true);
    }//GEN-LAST:event_btnNuevoProActionPerformed

    boolean cambiar = false;

    private void btnGuardarUsuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarUsuActionPerformed
        if (txtNombre.getText().equals("") || txtUsuario.getText().equals("") || txtPass.getPassword().equals("")) {
            JOptionPane.showMessageDialog(null, "Todo los campos son requeridos");
        } else {
            String usuario = txtUsuario.getText();
            String pass = String.valueOf(txtPass.getPassword());
            String nom = txtNombre.getText();
            String rol = cbxRol.getSelectedItem().toString();
            lg.setNombre(nom);
            lg.setUsuario(usuario);
            lg.setPass(pass);
            lg.setRol(rol);
            login.Registrar(lg);
            JOptionPane.showMessageDialog(null, "Usuario Registrado");
            LimpiarTabla(TableUsuarios);
            ListarUsuarios();
            nuevoUsuario();
        }
    }//GEN-LAST:event_btnGuardarUsuActionPerformed

    private void btnEliminarUsuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarUsuActionPerformed
        if (!"".equals(txtIdusuario.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdusuario.getText());
                login.eliminarUsuario(id);
                LimpiarTabla(TableUsuarios);
                LimpiarUsuarios();
                ListarUsuarios();
                btnEliminarUsu.setEnabled(false);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila");
        }
    }//GEN-LAST:event_btnEliminarUsuActionPerformed

    private void btnNuevoUsuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoUsuActionPerformed
        LimpiarUsuarios();
        btnEliminarUsu.setEnabled(false);
        btnGuardarUsu.setEnabled(true);
    }//GEN-LAST:event_btnNuevoUsuActionPerformed

    private void TableUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableUsuariosMouseClicked
        btnEliminarUsu.setEnabled(true);
        btnGuardarUsu.setEnabled(true);
        int fila = TableUsuarios.rowAtPoint(evt.getPoint());
        txtIdusuario.setText(TableUsuarios.getValueAt(fila, 0).toString());
        txtUsuario.setText(TableUsuarios.getValueAt(fila, 2).toString());
        txtNombre.setText(TableUsuarios.getValueAt(fila, 1).toString());
        cbxRol.setSelectedItem(TableUsuarios.getValueAt(fila, 3).toString());
    }//GEN-LAST:event_TableUsuariosMouseClicked

    private void txtCodigoVentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVentaKeyReleased
        if (!"".equals(txtCodigoVenta.getText())) {
            String cod = txtCodigoVenta.getText();
            pro = proDao.BuscarPro(cod);
            if (pro.getNombre() != null) {
                txtIdPro.setText("" + pro.getId());
                txtDescripcionVenta.setText("" + pro.getNombre());
                txtPrecioVenta.setText("" + pro.getPrecio());
                txtStockDisponible.setText("" + pro.getStock());
            }
        }
    }//GEN-LAST:event_txtCodigoVentaKeyReleased

    private void v_contadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_v_contadoActionPerformed
        txtDebe.setEnabled(false);
        LabelDebe.setEnabled(false);
        txtEntregado.setEnabled(false);
        LabelPago.setEnabled(false);
    }//GEN-LAST:event_v_contadoActionPerformed

    private void v_creditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_v_creditoActionPerformed
        txtDebe.setEnabled(true);
        LabelDebe.setEnabled(true);
        txtEntregado.setEnabled(true);
        LabelPago.setEnabled(true);
    }//GEN-LAST:event_v_creditoActionPerformed

    private void txtEntregadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEntregadoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtEntregado.getText())) {
                double pago = Double.parseDouble(txtEntregado.getText());
                double total = Double.parseDouble(LabelTotal.getText());
                double debe = total - pago;
                txtDebe.setText(String.valueOf(debe));
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el pago");
                txtEntregado.requestFocus();
            }
        }
    }//GEN-LAST:event_txtEntregadoKeyPressed

    private void txtEntregadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEntregadoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEntregadoKeyTyped

    private void txtDebeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDebeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDebeKeyPressed

    private void txtDebeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDebeKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDebeKeyTyped

    private void btnPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagarActionPerformed
        if ("".equals(txtIdVenta.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        } else {
            double pago = 0.0;
            int id = Integer.parseInt(txtIdVenta.getText());
            Vdao.ActualizarVenta(pago, "Cancelado", id);
            LimpiarTabla(TableVentas);
            ListarVentas();
            JOptionPane.showMessageDialog(null, "Venta Modificada");

        }
    }//GEN-LAST:event_btnPagarActionPerformed

    private void txtBuscarVentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarVentaKeyReleased
        buscarVenta(txtBuscarVenta.getText());
        if ("".equals(txtBuscarVenta.getText())) {
            LimpiarTabla(TableVentas);
            ListarVentas();
        }
    }//GEN-LAST:event_txtBuscarVentaKeyReleased

    private void txtPagoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPagoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!"".equals(txtEntregado.getText())) {
                double pago = Double.parseDouble(txtPago.getText());
                double total = Double.parseDouble(txtSaldo.getText());
                double debe = total - pago;
                txtdebe.setText(String.valueOf(debe));
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el pago");
                txtPago.requestFocus();
            }
        }
    }//GEN-LAST:event_txtPagoKeyPressed

    private void txtPagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPagoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPagoKeyTyped

    private void txtSaldoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSaldoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSaldoKeyPressed

    private void txtSaldoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSaldoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSaldoKeyTyped

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        if ("".equals(txtIdVenta.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        } else {
            double pago = Double.parseDouble(txtPago.getText());
            String estado = txtEstado.getText();
            int id = Integer.parseInt(txtIdVenta.getText());
            Vdao.ActualizarVenta(pago, estado, id);
            txtPago.setText("");
            txtSaldo.setText("");
            txtEstado.setText("");
            LimpiarTabla(TableVentas);
            ListarVentas();
            JOptionPane.showMessageDialog(null, "Venta Modificada");

        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sistema().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelDebe;
    private javax.swing.JLabel LabelDebe1;
    private javax.swing.JLabel LabelDebe2;
    private javax.swing.JLabel LabelPago;
    private javax.swing.JLabel LabelPago1;
    private javax.swing.JLabel LabelTotal;
    private javax.swing.JLabel LabelVendedor;
    private javax.swing.JTable TableCliente;
    private javax.swing.JTable TableProducto;
    private javax.swing.JTable TableProveedor;
    private javax.swing.JTable TableUsuarios;
    private javax.swing.JTable TableVenta;
    private javax.swing.JTable TableVentaDetallada;
    private javax.swing.JTable TableVentas;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnActualizarConfig;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnConfig;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnEditarProveedor;
    private javax.swing.JButton btnEditarpro;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnEliminarPro;
    private javax.swing.JButton btnEliminarProveedor;
    private javax.swing.JButton btnEliminarUsu;
    private javax.swing.JButton btnEliminarventa;
    private javax.swing.JButton btnGenerarVenta;
    private javax.swing.JButton btnGuardarCliente;
    private javax.swing.JButton btnGuardarUsu;
    private javax.swing.JButton btnGuardarpro;
    private javax.swing.JButton btnNuevaVenta;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnNuevoPro;
    private javax.swing.JButton btnNuevoProveedor;
    private javax.swing.JButton btnNuevoUsu;
    private javax.swing.JButton btnPagar;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnProveedor;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JButton btnVentaDetallada;
    private javax.swing.JButton btnVentas;
    private javax.swing.JButton btnguardarProveedor;
    private javax.swing.JComboBox<Object> cbxProveedorPro;
    private javax.swing.JComboBox<String> cbxRol;
    private javax.swing.JLabel d_cliente;
    private javax.swing.JLabel d_direccion;
    private javax.swing.JLabel d_dni;
    private javax.swing.JLabel d_fechaventa;
    private javax.swing.JLabel d_telefono;
    private javax.swing.JLabel d_total;
    private javax.swing.JLabel d_venta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField txtBuscarVenta;
    private javax.swing.JTextField txtCantPro;
    private javax.swing.JTextField txtCantidadVenta;
    private javax.swing.JTextField txtCodigoPro;
    private javax.swing.JTextField txtCodigoVenta;
    private javax.swing.JTextField txtDebe;
    private javax.swing.JTextField txtDesPro;
    private javax.swing.JTextField txtDescripcionVenta;
    private javax.swing.JTextField txtDireccionConfig;
    private javax.swing.JTextField txtDireccionProveedor;
    private javax.swing.JTextField txtDirecionCliente;
    private javax.swing.JTextField txtDniCliente;
    private javax.swing.JTextField txtDniVenta;
    private javax.swing.JTextField txtEntregado;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JLabel txtFechaVenta;
    private javax.swing.JTextField txtIdCV;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtIdConfig;
    private javax.swing.JTextField txtIdPro;
    private javax.swing.JTextField txtIdProveedor;
    private javax.swing.JTextField txtIdVenta;
    private javax.swing.JTextField txtIdproducto;
    private javax.swing.JLabel txtIdusuario;
    private javax.swing.JTextField txtMensaje;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNombreClienteventa;
    private javax.swing.JTextField txtNombreConfig;
    private javax.swing.JTextField txtNombreproveedor;
    private javax.swing.JTextField txtPago;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtPrecioPro;
    private javax.swing.JTextField txtPrecioVenta;
    private javax.swing.JTextField txtRucConfig;
    private javax.swing.JTextField txtRucProveedor;
    private javax.swing.JTextField txtSaldo;
    private javax.swing.JTextField txtStockDisponible;
    private javax.swing.JTextField txtTelefonoCliente;
    private javax.swing.JTextField txtTelefonoConfig;
    private javax.swing.JTextField txtTelefonoProveedor;
    private javax.swing.JTextField txtUsuario;
    private javax.swing.JLabel txtdebe;
    private javax.swing.JRadioButton v_contado;
    private javax.swing.JRadioButton v_credito;
    // End of variables declaration//GEN-END:variables

    private void LimpiarCliente() {
        txtIdCliente.setText("");
        txtDniCliente.setText("");
        txtNombreCliente.setText("");
        txtTelefonoCliente.setText("");
        txtDirecionCliente.setText("");
        txtEntregado.setText("");
        txtDebe.setText("0.0");
    }

    private void LimpiarProveedor() {
        txtIdProveedor.setText("");
        txtRucProveedor.setText("");
        txtNombreproveedor.setText("");
        txtTelefonoProveedor.setText("");
        txtDireccionProveedor.setText("");
    }

    private void LimpiarProductos() {
        txtIdPro.setText("");
        txtCodigoPro.setText("");
        cbxProveedorPro.setSelectedItem(null);
        txtDesPro.setText("");
        txtCantPro.setText("");
        txtPrecioPro.setText("");
    }

    private void LimpiarUsuarios() {
        txtUsuario.setText("");
        txtPass.setText("");
        cbxRol.setSelectedItem(null);
        txtNombre.setText("");
    }

    private void LimpiarDetalle() {
        d_dni.setText("");
        d_cliente.setText("");
        d_telefono.setText("");
        d_direccion.setText("");
        d_venta.setText("");
        d_fechaventa.setText("");
        d_total.setText("");
    }

    private void TotalPagar() {
        Totalpagar = 0.00;
        int numFila = TableVenta.getRowCount();
        for (int i = 0; i < numFila; i++) {
            double cal = Double.parseDouble(String.valueOf(TableVenta.getModel().getValueAt(i, 4)));
            Totalpagar = Totalpagar + cal;
        }
        LabelTotal.setText(String.format("%.2f", Totalpagar));
    }

    private void LimparVenta() {
        txtCodigoVenta.setText("");
        txtDescripcionVenta.setText("");
        txtCantidadVenta.setText("");
        txtStockDisponible.setText("");
        txtPrecioVenta.setText("");
        txtIdVenta.setText("");
    }

    private void RegistrarVenta() {
        int cliente = Integer.parseInt(txtIdCV.getText());
        double debe = Double.parseDouble(txtDebe.getText());
        String vendedor = LabelVendedor.getText();
        double monto = Totalpagar;
        v.setCliente(cliente);
        v.setVendedor(vendedor);
        v.setTotal(monto);
        v.setFecha(fechaActual);
        v.setDebe(debe);
        if (v_contado.isSelected() == true) {
            v.setEstado("Cancelado");
        }
        if (v_credito.isSelected() == true) {
            v.setEstado("Pendiente");
        }
        Vdao.RegistrarVenta(v);
    }

    public void buscarVenta(String buscar) {
        DefaultTableModel modelo = Vdao.Buscar(buscar);
        TableVentas.setModel(modelo);
    }

    private void RegistrarDetalle() {
        int id = Vdao.IdVenta();
        for (int i = 0; i < TableVenta.getRowCount(); i++) {
            int id_pro = Integer.parseInt(TableVenta.getValueAt(i, 0).toString());
            int cant = Integer.parseInt(TableVenta.getValueAt(i, 2).toString());
            double precio = Double.parseDouble(TableVenta.getValueAt(i, 3).toString());
            Dv.setId_pro(id_pro);
            Dv.setCantidad(cant);
            Dv.setPrecio(precio);
            Dv.setId(id);
            Vdao.RegistrarDetalle(Dv);
        }
        int cliente = Integer.parseInt(txtIdCV.getText());
        ventaDt(id, cliente, Totalpagar, txtFechaVenta.getText());
    }

    private void ActualizarStock() {
        for (int i = 0; i < TableVenta.getRowCount(); i++) {
            int id = Integer.parseInt(TableVenta.getValueAt(i, 0).toString());
            int cant = Integer.parseInt(TableVenta.getValueAt(i, 2).toString());
            pro = proDao.BuscarId(id);
            int StockActual = pro.getStock() - cant;
            Vdao.ActualizarStock(StockActual, id);
        }
    }

    private void LimpiarTabla(JTable tabla) {
        tmp = (DefaultTableModel) tabla.getModel();
        int fila = tabla.getRowCount();
        for (int i = 0; i < fila; i++) {
            tmp.removeRow(0);
        }
    }

    private void LimpiarClienteventa() {
        txtDniVenta.setText("");
        txtNombreClienteventa.setText("");
        txtIdCV.setText("");
    }

    private void nuevoUsuario() {
        txtNombre.setText("");
        txtUsuario.setText("");
        txtPass.setText("");
    }

    private void llenarProveedor() {
        List<ProveedorTo> lista = PrDao.ListarProveedor();
        for (int i = 0; i < lista.size(); i++) {
            int id = lista.get(i).getId();
            String nombre = lista.get(i).getNombre();
            cbxProveedorPro.addItem(new ComboTo(id, nombre));
        }
    }

    private void modificarTabla(JTable tabla) {
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabla.getTableHeader().setOpaque(false);
        tabla.getTableHeader().setBackground(Color.BLACK);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setRowHeight(20);
    }

    public void ventaDt(int idventa, int Cliente, double total, String fecha) {
        String prove = "SELECT * FROM clientes WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(prove);
            ps.setInt(1, Cliente);
            rs = ps.executeQuery();
            if (rs.next()) {
                d_dni.setText(rs.getString("dni"));
                d_cliente.setText(rs.getString("nombre"));
                d_telefono.setText(rs.getString("telefono"));
                d_direccion.setText(rs.getString("direccion"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        d_venta.setText(String.valueOf(idventa));
        d_fechaventa.setText(fecha);
        d_total.setText("S/." + String.valueOf(total));
        String product = "SELECT d.id, d.id_pro,d.id_venta, d.precio, d.cantidad, p.id, p.nombre FROM detalle d INNER JOIN productos p ON d.id_pro = p.id WHERE d.id_venta = ?";
        try {
            ps = con.prepareStatement(product);
            ps.setInt(1, idventa);
            rs = ps.executeQuery();
            while (rs.next()) {
                modelo = (DefaultTableModel) TableVentaDetallada.getModel();
                Object[] ob = new Object[4];
                double subTotal = rs.getInt("cantidad") * rs.getDouble("precio");
                ob[0] = rs.getString("cantidad");
                ob[1] = rs.getString("nombre");
                ob[2] = rs.getString("precio");
                ob[3] = String.valueOf(subTotal);
                modelo.addRow(ob);
                TableVentaDetallada.setModel(modelo);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

}
