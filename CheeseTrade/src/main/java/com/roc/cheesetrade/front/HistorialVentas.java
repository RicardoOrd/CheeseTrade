/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.roc.cheesetrade.front;

import com.roc.cheesetrade.dao.HistorialDAO;
import com.roc.cheesetrade.entities.DetalleHistorial;
import com.roc.cheesetrade.entities.VentaHistorial;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class HistorialVentas extends javax.swing.JFrame {

    public HistorialVentas() {
        initComponents();
        cargarHistorial();
        ajustarColumnas();
        centrarColumnas();
    }

    // Ajusta el ancho de las columnas
    private void ajustarColumnas() {
        tblHistorial.getColumnModel().getColumn(0).setPreferredWidth(25); // ID (más corto)
        tblHistorial.getColumnModel().getColumn(1).setPreferredWidth(110); // Cliente
        tblHistorial.getColumnModel().getColumn(2).setPreferredWidth(130); // Fecha y hora
        tblHistorial.getColumnModel().getColumn(3).setPreferredWidth(80); // Estado
        tblHistorial.getColumnModel().getColumn(4).setPreferredWidth(70); // Total
        tblHistorial.getColumnModel().getColumn(5).setPreferredWidth(120); // Método de Pago
    }

    // Centra columnas específicas
    private void centrarColumnas() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tblHistorial.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // ID
        tblHistorial.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Estado
        tblHistorial.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Total
    }

    private void cargarHistorial() {
        HistorialDAO historialDAO = new HistorialDAO();
        List<VentaHistorial> historial = historialDAO.obtenerHistorialCompleto();

        DefaultTableModel modelo = (DefaultTableModel) tblHistorial.getModel();
        modelo.setRowCount(0); // Limpia la tabla

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));

        for (VentaHistorial venta : historial) {
            // Sumar la cantidad total de productos de la venta
            int cantidadTotal = 0;
            for (DetalleHistorial det : venta.getDetalles()) {
                cantidadTotal += det.getCantidad();
            }
            modelo.addRow(new Object[]{
                venta.getIdVenta(),
                venta.getUsuario(),
                sdf.format(venta.getFecha()),
                cantidadTotal, // <-- Aquí va la cantidad total de productos
                currencyFormat.format(venta.getTotal()), // <-- El total de la venta
                venta.getMetodoPago() // <-- El método de pago, si lo tienes
            });
            for (DetalleHistorial det : venta.getDetalles()) {
                modelo.addRow(new Object[]{
                    "",
                    "    - " + det.getNombreProducto(),
                    "",
                    det.getCantidad(), // <-- Cantidad de este producto
                    currencyFormat.format(det.getSubtotal()), // <-- Subtotal de este producto
                    ""
                });
            }
        }
    }

    private void cargarHistorialFiltrado() {
        String usuarioFiltro = txtUsuarioFiltro.getText();
        java.util.Date fechaInicio = dateInicio.getDate();
        java.util.Date fechaFin = dateFin.getDate();

        HistorialDAO historialDAO = new HistorialDAO();
        List<VentaHistorial> historial = historialDAO.obtenerHistorialFiltrado(usuarioFiltro, fechaInicio, fechaFin);

        DefaultTableModel modelo = (DefaultTableModel) tblHistorial.getModel();
        modelo.setRowCount(0);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));

        for (VentaHistorial venta : historial) {
            int cantidadTotal = 0;
            for (DetalleHistorial det : venta.getDetalles()) {
                cantidadTotal += det.getCantidad();
            }
            modelo.addRow(new Object[]{
                venta.getIdVenta(),
                venta.getUsuario(),
                sdf.format(venta.getFecha()),
                cantidadTotal,
                currencyFormat.format(venta.getTotal()),
                venta.getMetodoPago()
            });
            for (DetalleHistorial det : venta.getDetalles()) {
                modelo.addRow(new Object[]{
                    "",
                    "    - " + det.getNombreProducto(),
                    "",
                    det.getCantidad(),
                    currencyFormat.format(det.getSubtotal()),
                    ""
                });
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblHistorial = new javax.swing.JTable();
        btnActualizar = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();
        txtUsuarioFiltro = new javax.swing.JTextField();
        dateInicio = new com.toedter.calendar.JDateChooser();
        dateFin = new com.toedter.calendar.JDateChooser();
        btnFiltrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        tblHistorial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Cliente", "Fecha y hora", "Cantidad", "Total", "Metodo De  Pago"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblHistorial);

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnExportar.setText("Exportar");
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        txtUsuarioFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioFiltroActionPerformed(evt);
            }
        });

        btnFiltrar.setText("Filtrar");
        btnFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Historial de Ventas");

        jButton1.setText("Salir");
        jButton1.setMaximumSize(new java.awt.Dimension(79, 27));
        jButton1.setMinimumSize(new java.awt.Dimension(79, 27));
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(txtUsuarioFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(dateInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(dateFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnFiltrar))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 673, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(213, 213, 213)
                        .addComponent(btnActualizar)
                        .addGap(18, 18, 18)
                        .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUsuarioFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFiltrar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizar)
                    .addComponent(btnExportar)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        cargarHistorial();

        // TODO add your handling code here:
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Historial de Ventas");
            DefaultTableModel model = (DefaultTableModel) tblHistorial.getModel();

            // Encabezados
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < model.getColumnCount(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(model.getColumnName(i));
            }

            // Datos
            for (int i = 0; i < model.getRowCount(); i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < model.getColumnCount(); j++) {
                    Object value = model.getValueAt(i, j);
                    row.createCell(j).setCellValue(value != null ? value.toString() : "");
                }
            }

            // Guardar archivo
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar como...");
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                if (!path.endsWith(".xlsx")) {
                    path += ".xlsx";
                }
                try (FileOutputStream out = new FileOutputStream(path)) {
                    workbook.write(out);
                    workbook.close();
                    javax.swing.JOptionPane.showMessageDialog(this, "Archivo exportado correctamente.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Error al exportar: " + e.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnExportarActionPerformed

    private void txtUsuarioFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuarioFiltroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuarioFiltroActionPerformed

    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        cargarHistorialFiltrado();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFiltrarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnFiltrar;
    private com.toedter.calendar.JDateChooser dateFin;
    private com.toedter.calendar.JDateChooser dateInicio;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblHistorial;
    private javax.swing.JTextField txtUsuarioFiltro;
    // End of variables declaration//GEN-END:variables
}
