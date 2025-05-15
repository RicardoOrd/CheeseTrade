/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.roc.cheesetrade.front;

import com.roc.cheesetrade.entities.Inv;
import com.roc.cheesetrade.dao.InvDAO;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author carl-
 */
public class Inventario extends javax.swing.JFrame {

    private InvDAO invDAO;
    private DefaultTableModel modeloTabla;
    /**
     * Creates new form ClientesForm
     */
    public Inventario() {
        initComponents();
        
        invDAO = new InvDAO();
        
        modeloTabla = (DefaultTableModel) tblClientes.getModel();
        
        invDAO.obtenerListadoProd(modeloTabla);
        
        agregarBotones();
        
        tblClientes.removeColumn(tblClientes.getColumn("ID"));
        
        tblClientes.setRowHeight(25);
        
        configurarColumnas();
         
    }
    
    private void configurarColumnas(){
        TableColumnModel modeloColumnas = tblClientes.getColumnModel();
        
        int totalAncho = tblClientes.getWidth();
        
        int anchoCol1 = (int) (totalAncho * 0.18);
        int anchoCol2 = (int) (totalAncho * 0.42);
        int anchoCol3 = (int) (totalAncho * 0.08);
        int anchoCol4 = (int) (totalAncho * 0.05);
        int anchoCol5 = (int) (totalAncho * 0.09);
        int anchoCol6 = (int) (totalAncho * 0.08);
        int anchoCol7 = (int) (totalAncho * 0.05);
        int anchoCol8 = (int) (totalAncho * 0.05);


        
        modeloColumnas.getColumn(0).setPreferredWidth(anchoCol1);
        modeloColumnas.getColumn(1).setPreferredWidth(anchoCol2);
        modeloColumnas.getColumn(2).setPreferredWidth(anchoCol3);
        modeloColumnas.getColumn(3).setPreferredWidth(anchoCol4);
        modeloColumnas.getColumn(4).setPreferredWidth(anchoCol5);
        modeloColumnas.getColumn(5).setPreferredWidth(anchoCol6);
        modeloColumnas.getColumn(6).setPreferredWidth(anchoCol7);
        modeloColumnas.getColumn(7).setPreferredWidth(anchoCol8);


    }
    
    private void agregarBotones() {
        
        tblClientes.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mousePressed(MouseEvent e) {
                int columna = tblClientes.columnAtPoint(e.getPoint());
                int fila = tblClientes.rowAtPoint(e.getPoint());
                
                if(columna == tblClientes.getColumnModel().getColumnIndex("Edit")
                        && columna == tblClientes.getColumnModel().getColumnIndex("Elim")){
                    
                    if(e.getButton() == MouseEvent.BUTTON1){
                        
                        if (tblClientes.isCellEditable(fila, columna)){
                            
                            tblClientes.editCellAt(fila, columna);
                        }
                    }
                }
            }
     
        });
        
        tblClientes.getColumnModel().getColumn(tblClientes.getColumnModel().getColumnIndex("Edit")).setCellRenderer( 
                new TableCellRenderer() {
            
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                
                JButton btnEditar = new JButton(new ImageIcon(getClass().getResource("/edit.png")));
                
                btnEditar.setPreferredSize(new Dimension(40,25));
                
                return btnEditar;
            }
        }        );
        

        tblClientes.getColumnModel().getColumn(tblClientes.getColumnModel().getColumnIndex("Elim")).setCellRenderer( 
                new TableCellRenderer() {
            
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                
                JButton btnEliminar = new JButton(new ImageIcon(getClass().getResource("/delete.png")));
                
                btnEliminar.setPreferredSize(new Dimension(40,25));
                
                return btnEliminar;
            }
        } );
        
        tblClientes.getColumnModel().getColumn(
        tblClientes.getColumnModel().getColumnIndex("Edit")).setCellEditor(new AccionBotones());
        
        tblClientes.getColumnModel().getColumn(
        tblClientes.getColumnModel().getColumnIndex("Elim")).setCellEditor(new AccionBotones());
        
    }
    
    class AccionBotones extends AbstractCellEditor implements TableCellEditor{
        
        private final JButton btnEditar;
        private final JButton btnEliminar;

        
        public AccionBotones(){
            btnEditar = new JButton(new ImageIcon(getClass().getResource("/edit.png")));
            btnEditar.setPreferredSize(new Dimension(40,25));
            
            btnEliminar = new JButton(new ImageIcon(getClass().getResource("/delete.png")));
            btnEliminar.setPreferredSize(new Dimension(40,25));
            
            btnEditar.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = tblClientes.getSelectedRow();
                    int idCliente = (int) modeloTabla.getValueAt(selectedRow, 0);

//                    JOptionPane.showMessageDialog(null,"Se editará el cliente con ID: " 
//                            + idCliente + ", Nombre: "+nombreCliente,
//                            "Aviso",
//                            JOptionPane.WARNING_MESSAGE
//                            );
                    Editar_Agregar edicionInventario = new Editar_Agregar();

                    edicionInventario.esEdicion = true; // <- ¡Importante!

                    
                    Inv productoAEditar = invDAO.obtenerProducto(idCliente);

                    edicionInventario.elProd = productoAEditar;

                    edicionInventario.llenarDatosProd();
                    
                    edicionInventario.actualizarTitulo(); // <-- Puedes llamarlo aquí por claridad

                    edicionInventario.setVisible(true);

                    edicionInventario.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            actualizarListadoClientes();

                            tblClientes.setRowSelectionInterval(selectedRow, selectedRow);
                        }

                    });
                }
            });
          
            btnEliminar.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    int idProd = (int) modeloTabla.getValueAt(tblClientes.getSelectedRow(), 0);
                    String nombreProd = modeloTabla.getValueAt(tblClientes.getSelectedRow(), 1).toString();

//                    JOptionPane.showMessageDialog(null, "Se eliminará el cliente con ID: "
//                            + idCliente + ", Nombre: " + nombreCliente,
//                            "Aviso",
//                            JOptionPane.WARNING_MESSAGE
//                    );
                    int seleccion = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el Producto con ID: "
                            + idProd + ", Nombre: " + nombreProd + "?",
                            "Confirmar Eliminar", JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

                    if (seleccion == JOptionPane.OK_OPTION) {

                        int registrosAfectados = invDAO.eliminarProd(idProd);

                        if (registrosAfectados > 0) {
                            System.out.println("SE ELIMINÓ EL PRODUCTO CORRECTAMENTE");

                            JOptionPane.showMessageDialog(null, "SE ELIMINÓ EL PRODUCTO "
                                    + nombreProd + " CORRECTAMENTE", "AVISO DE DELETE",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "NO SE PUDO ELIMINAR EL PRODUCTO "
                                    + nombreProd + " CORRECTAMENTE", "AVISO DE DELETE",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        
                        if(tblClientes.isEditing()){
                            tblClientes.getCellEditor().stopCellEditing();
                        }
                        
                        actualizarListadoClientes();
                    }

                }

            });
        }
        
        @Override
        public Object getCellEditorValue() {
            return null;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if(column == tblClientes.getColumnModel().getColumnIndex("Edit")){
                return btnEditar;
            } else if (column == tblClientes.getColumnModel().getColumnIndex("Elim")){
                return btnEliminar;
            } else {
                return null;
            }
        }

    }
    
    public void actualizarListadoClientes(){
        modeloTabla.setRowCount(0);
        
        invDAO.obtenerListadoProd(modeloTabla);
        
        configurarColumnas();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblInquilinos = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Clientes");

        lblInquilinos.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        lblInquilinos.setText("Inventario");

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Descripcion", "Precio", "Stock", "Peso Unit", "Tipo", "Edit", "Elim"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        btnAgregar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setText("Salir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblInquilinos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 871, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInquilinos)
                    .addComponent(btnAgregar)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        Editar_Agregar nuevoProducto = new Editar_Agregar();
        
        nuevoProducto.esEdicion = false; // <- ¡Muy importante!
        nuevoProducto.actualizarTitulo(); // <-- Llama al actualizar título
        
        nuevoProducto.setVisible(true);
        
        nuevoProducto.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosed(WindowEvent e) {
                actualizarListadoClientes();
            }
            
        });
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblInquilinos;
    private javax.swing.JTable tblClientes;
    // End of variables declaration//GEN-END:variables
}
