package bmr_main_package;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Krzysztof
 */
public class BMR_Main extends javax.swing.JFrame
{

    private enum FrameId
    {
        INITIAL, MEASURES_LIST
    };

    // Class attributes
    private SettingsData settingsData = new SettingsData();
    private MeasuresData measuresData = new MeasuresData();
    private FrameId currentFrameId = FrameId.INITIAL;

    // private MeasureForm measureForm = new MeasureForm();    // Access to class attributes and classs functions
    private void DisplayFrame(FrameId newFrameId)
    {
        if (currentFrameId != newFrameId)
        {
            //JInternalFrame frameOld;
            JInternalFrame frameNew;

            switch (newFrameId)
            {
                case MEASURES_LIST:
                default:
                    frameNew = this.frameMeasuresList;
                    break;
            }

            try
            {
                frameNew.setVisible(true);
                frameNew.setMaximum(true);
                currentFrameId = newFrameId;
            } catch (PropertyVetoException e)
            {
                System.out.println(e.getMessage());
            }

        }
    }



    private void MeasuresListToScreen()
    {
        BMR_Measures measures = this.measuresData.getBMR_Measures();
        if (measures == null)
        {
            return;
        }

        measures.sortMeasures();
        DefaultTableModel model = (DefaultTableModel) tableMeasuresList.getModel();
        while (model.getRowCount() > 0)
        {
            model.removeRow(0);
        }

        for (Measure measure : measures.getAllMeasures())
        {
            Category categoryWHO = measure.getCategoryWHO();

            model.addRow(new Object[]
            {
                measure.getDate(),
                measure.getHour(),
                categoryWHO.getColorObject(),
                measure.getSystolicAsInt(),
                measure.getDiastolicAsInt(),
                measure.getPulseAsInt(),
                measure.getArrhythmiaAsYes(),
                measure.getGlucoseAsInt(),
                measure.getEmptyStomachAsYes(),
                measure.getComment()
            });

        }
    }

    private void setFrameIcon()
    {       
        //this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/bmr_main_package/Icons/heart-beat-icon (64x64).png")).getImage());

        //String iconFileName = "C:\\JavaPodstawy\\MyProjects\\BloodMeasures\\BMR2\\src\\bmr_main_package\\Icons\\heart-beat-icon (64x64).png";
        //this.setIconImage(Toolkit.getDefaultToolkit().getImage(iconFileName));
     }



    private void InitMeasuresTable()
    {
        // Allignment and color common for entire column
        ColumnRenderer crBlue = new ColumnRenderer(SwingConstants.RIGHT, Color.darkGray);

        for (int i = 0; i < tableMeasuresList.getColumnCount(); i++)
        {
            TableColumn column = tableMeasuresList.getColumnModel().getColumn(i);

            switch ((String) column.getHeaderValue())
            {
                case "Date":
                    setTableColumnSize(column, 80);
                    break;
                case "Hour":
                    setTableColumnSize(column, 50);
                    break;
                case "WHO":
                    setTableColumnSize(column, 40);
                    // Backgound color depends on color passed as cell value
                    column.setCellRenderer(new ColorCellRenderer());
                    break;
                case "Systolic":
                case "Diastolic":
                case "Pulse":
                    setTableColumnSize(column, 60);
                    column.setCellRenderer(crBlue);
                    break;
                case "Glucose":
                    setTableColumnSize(column, 60);
                    column.setCellRenderer(new ColumnRenderer(SwingConstants.RIGHT, Color.gray));
                    break;
                case "Arrhythmia":
                    setTableColumnSize(column, 70);
                    column.setCellRenderer(new ColumnRenderer(SwingConstants.CENTER, Color.darkGray));
                    break;
                case "Empty stomach":
                    setTableColumnSize(column, 95);
                    column.setCellRenderer(new ColumnRenderer(SwingConstants.CENTER, Color.gray));
                    break;
                default:
                    break;
            }
        }
        /*
        TableCellRenderer tcr = new ColorCellRenderer();
        TableColumn column = tableMeasuresList.getColumnModel().getColumn(2);
        column.setCellRenderer(tcr);
         */
    }

    
    private void setTableColumnSize(TableColumn column, int newSize)
    {
        column.setWidth(newSize);
        column.setPreferredWidth(newSize);
        column.setMaxWidth(newSize);
        column.setMinWidth(newSize / 2);
    }

    // Store new/udated measure
    private void WriteMeasure()
    {
        // Sort list of measures
        this.measuresData.getBMR_Measures().sortMeasures();

        // Store data - Write XML file 
        this.measuresData.WriteMeasuresXmlFile();
    }

    private Settings getSettings()
    {
        Settings settings = null;

        for (Settings item : this.settingsData.getBMR_Settings().getAllSettings())
        {
            settings = item;
        }
        
        return settings;
    }
    
    private String getMeasuresXmlFileName()
    {
        String fileName = "";
       
        if (this.getSettings() != null)
        {
            fileName = this.getSettings().getMeasuresXmlFile();
        }

        return fileName;
    }

    /**
     * Creates new form BMR_Main
     *
     * @param fileName - Name of file with settings. Optional application
     * parameter
     */
    public BMR_Main(String fileName)
    {
        this.settingsData.ReadSettingsXmlFile(fileName);
        this.measuresData.ReadMeasuresXmlFile(getMeasuresXmlFileName());

        initComponents();
        setFrameIcon();
        InitMeasuresTable();

        MeasuresListToScreen();

        DisplayFrame(FrameId.MEASURES_LIST);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        jMenu2 = new JMenu();
        toolsPanel = new JPanel();
        butSettings = new JButton();
        butExportMeasures = new JToggleButton();
        butWHO = new JButton();
        desktop = new JDesktopPane();
        frameMeasuresList = new JInternalFrame();
        measuresToolsPanel = new JPanel();
        butAddMeasure = new JButton();
        butEditMeasure = new JButton();
        butDeleteMeasure = new JButton();
        measuresTablePanel = new JPanel();
        paneMeasuresList = new JScrollPane();
        tableMeasuresList = new JTable();

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText("File");
        fileMenu.setName("fileMenu"); // NOI18N
        menuBar.add(fileMenu);

        jMenu2.setText("Edit");
        jMenu2.setName("jMenu2"); // NOI18N
        menuBar.add(jMenu2);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("BMR2 - Blood Measures Registration");
        setIconImage(new ImageIcon(getClass().getResource("/bmr_main_package/Icons/heart-beat-icon (64x64).png")).getImage());
        setName("Frame"); // NOI18N

        toolsPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        toolsPanel.setName("toolsPanel"); // NOI18N

        butSettings.setIcon(new ImageIcon(getClass().getResource("/bmr_main_package/Icons/settings-icon (64x64).png"))); // NOI18N
        butSettings.setToolTipText("Settings");
        butSettings.setName("butSettings"); // NOI18N
        butSettings.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                butSettingsActionPerformed(evt);
            }
        });

        butExportMeasures.setIcon(new ImageIcon(getClass().getResource("/bmr_main_package/Icons/Export-To-File-icon (64x64).png"))); // NOI18N
        butExportMeasures.setToolTipText("Export measures to file");
        butExportMeasures.setName("butExportMeasures"); // NOI18N
        butExportMeasures.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                butExportMeasuresActionPerformed(evt);
            }
        });

        butWHO.setIcon(new ImageIcon(getClass().getResource("/bmr_main_package/Icons/heart-beat-icon (64x64).png"))); // NOI18N
        butWHO.setToolTipText("Blood pressure WHO categories");
        butWHO.setName("butWHO"); // NOI18N
        butWHO.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                butWHOActionPerformed(evt);
            }
        });

        GroupLayout toolsPanelLayout = new GroupLayout(toolsPanel);
        toolsPanel.setLayout(toolsPanelLayout);
        toolsPanelLayout.setHorizontalGroup(toolsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(butExportMeasures, GroupLayout.Alignment.TRAILING)
            .addComponent(butSettings, GroupLayout.Alignment.TRAILING)
            .addComponent(butWHO, GroupLayout.Alignment.TRAILING)
        );
        toolsPanelLayout.setVerticalGroup(toolsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(toolsPanelLayout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(butExportMeasures)
                .addGap(18, 18, 18)
                .addComponent(butSettings)
                .addGap(18, 18, 18)
                .addComponent(butWHO)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        desktop.setName("desktop"); // NOI18N

        frameMeasuresList.setTitle("Measures list");
        frameMeasuresList.setName("frameMeasuresList"); // NOI18N
        frameMeasuresList.setVisible(true);

        measuresToolsPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        measuresToolsPanel.setName("measuresToolsPanel"); // NOI18N

        butAddMeasure.setIcon(new ImageIcon(getClass().getResource("/bmr_main_package/Icons/table-add-icon.png"))); // NOI18N
        butAddMeasure.setToolTipText("Register new measure");
        butAddMeasure.setName("butAddMeasure"); // NOI18N
        butAddMeasure.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                butAddMeasureActionPerformed(evt);
            }
        });

        butEditMeasure.setIcon(new ImageIcon(getClass().getResource("/bmr_main_package/Icons/table-edit-icon.png"))); // NOI18N
        butEditMeasure.setToolTipText("Update selected measure");
        butEditMeasure.setName("butEditMeasure"); // NOI18N
        butEditMeasure.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                butEditMeasureActionPerformed(evt);
            }
        });

        butDeleteMeasure.setIcon(new ImageIcon(getClass().getResource("/bmr_main_package/Icons/table-remove-icon.png"))); // NOI18N
        butDeleteMeasure.setToolTipText("Remove selected measure");
        butDeleteMeasure.setName("butDeleteMeasure"); // NOI18N
        butDeleteMeasure.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                butDeleteMeasureActionPerformed(evt);
            }
        });

        GroupLayout measuresToolsPanelLayout = new GroupLayout(measuresToolsPanel);
        measuresToolsPanel.setLayout(measuresToolsPanelLayout);
        measuresToolsPanelLayout.setHorizontalGroup(measuresToolsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(measuresToolsPanelLayout.createSequentialGroup()
                .addGap(317, 317, 317)
                .addComponent(butAddMeasure)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(butEditMeasure)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(butDeleteMeasure)
                .addContainerGap(352, Short.MAX_VALUE))
        );

        measuresToolsPanelLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {butAddMeasure, butDeleteMeasure, butEditMeasure});

        measuresToolsPanelLayout.setVerticalGroup(measuresToolsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(measuresToolsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(measuresToolsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(butAddMeasure)
                    .addComponent(butEditMeasure, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(butDeleteMeasure, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        measuresToolsPanelLayout.linkSize(SwingConstants.VERTICAL, new Component[] {butDeleteMeasure, butEditMeasure});

        measuresTablePanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        measuresTablePanel.setName("measuresTablePanel"); // NOI18N

        paneMeasuresList.setName("paneMeasuresList"); // NOI18N

        tableMeasuresList.setModel(new DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "Date", "Hour", "WHO", "Systolic", "Diastolic", "Pulse", "Arrhythmia", "Glucose", "Empty stomach", "Comment"
            }
        )
        {
            Class[] types = new Class []
            {
                String.class, String.class, Object.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class, String.class, String.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        tableMeasuresList.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        tableMeasuresList.setName("tableMeasuresList"); // NOI18N
        tableMeasuresList.setNextFocusableComponent(butAddMeasure);
        tableMeasuresList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        paneMeasuresList.setViewportView(tableMeasuresList);

        GroupLayout measuresTablePanelLayout = new GroupLayout(measuresTablePanel);
        measuresTablePanel.setLayout(measuresTablePanelLayout);
        measuresTablePanelLayout.setHorizontalGroup(measuresTablePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(paneMeasuresList)
        );
        measuresTablePanelLayout.setVerticalGroup(measuresTablePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(paneMeasuresList, GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
        );

        GroupLayout frameMeasuresListLayout = new GroupLayout(frameMeasuresList.getContentPane());
        frameMeasuresList.getContentPane().setLayout(frameMeasuresListLayout);
        frameMeasuresListLayout.setHorizontalGroup(frameMeasuresListLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(measuresTablePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(measuresToolsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        frameMeasuresListLayout.setVerticalGroup(frameMeasuresListLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(frameMeasuresListLayout.createSequentialGroup()
                .addComponent(measuresTablePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(measuresToolsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        desktop.setLayer(frameMeasuresList, JLayeredPane.DEFAULT_LAYER);

        GroupLayout desktopLayout = new GroupLayout(desktop);
        desktop.setLayout(desktopLayout);
        desktopLayout.setHorizontalGroup(desktopLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(frameMeasuresList)
        );
        desktopLayout.setVerticalGroup(desktopLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(desktopLayout.createSequentialGroup()
                .addComponent(frameMeasuresList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(desktop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(desktop)
            .addComponent(toolsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void butSettingsActionPerformed(ActionEvent evt)//GEN-FIRST:event_butSettingsActionPerformed
    {//GEN-HEADEREND:event_butSettingsActionPerformed
        // Create modal window
        SettingsForm settingsForm = new SettingsForm(this.getSettings());
        settingsForm.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        settingsForm.setTitle("Settings");

        settingsForm.setVisible(true);   // Data registration in modal window

        // Data storage
        if (settingsForm.isSaved() == true)
        {
            // Data storage. Settings update is not necessary because update works on reference (not on a settings copy)
            this.settingsData.WriteSettingsXmlFile();
        }    
    }//GEN-LAST:event_butSettingsActionPerformed

    private void butAddMeasureActionPerformed(ActionEvent evt)//GEN-FIRST:event_butAddMeasureActionPerformed
    {//GEN-HEADEREND:event_butAddMeasureActionPerformed
        //System.out.println("Pre Add Measure: " + evt.paramString());

        // Create modal window
        MeasureForm measureForm = new MeasureForm();
        measureForm.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        measureForm.setTitle("Register new measure");

        measureForm.setVisible(true);   // Data registration in modal window

        // Data storage
        if (measureForm.isSaved() == true)
        {
            this.measuresData.getBMR_Measures().addNewMeasure(measureForm.getCurMeasure());
            WriteMeasure();
            MeasuresListToScreen();
        }

        //System.out.println("Post Add Measure: " + evt.paramString());
    }//GEN-LAST:event_butAddMeasureActionPerformed

    private void butEditMeasureActionPerformed(ActionEvent evt)//GEN-FIRST:event_butEditMeasureActionPerformed
    {//GEN-HEADEREND:event_butEditMeasureActionPerformed
        if (this.tableMeasuresList.getSelectedRow() == -1)
        {
            if (this.tableMeasuresList.getRowCount() == 0)
            {
                CommonDefs.infoBox("You cannot edit a measure - Measures table is empty", "Measure selection");
            } else
            {
                CommonDefs.infoBox("Please select a measure to be updated", "Measure selection");
            }
            return;
        }

        // Create modal window
        MeasureForm measureForm = new MeasureForm(this.measuresData.getBMR_Measures().getMeasure(this.tableMeasuresList.getSelectedRow()));
        measureForm.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        measureForm.setTitle("Update measure");

        measureForm.setVisible(true);   // Data registration in modal window

        // Data storage. Measure update in measures list is not necessary because edition works on reference (not on a measure copy)
        if (measureForm.isSaved() == true)
        {
            WriteMeasure();
            MeasuresListToScreen();
        }

        //System.out.println("Post Edit Measure: " + evt.paramString());
    }//GEN-LAST:event_butEditMeasureActionPerformed

    private void butDeleteMeasureActionPerformed(ActionEvent evt)//GEN-FIRST:event_butDeleteMeasureActionPerformed
    {//GEN-HEADEREND:event_butDeleteMeasureActionPerformed
        if (this.tableMeasuresList.getSelectedRow() == -1)
        {
            if (this.tableMeasuresList.getRowCount() == 0)
            {
                CommonDefs.infoBox("You cannot delete a measure - Measures table is empty", "Measure selection");
            } else
            {
                CommonDefs.infoBox("Please select a measure to be deleted", "Measure selection");
            }
            return;
        }

        if (CommonDefs.YesNoBox("Delete selected measure (Yes/No)?", "Measure delete") == true)
        {
            this.measuresData.getBMR_Measures().removeMeasure(this.tableMeasuresList.getSelectedRow());
            WriteMeasure();
            MeasuresListToScreen();
        }
    }//GEN-LAST:event_butDeleteMeasureActionPerformed

    private void butExportMeasuresActionPerformed(ActionEvent evt)//GEN-FIRST:event_butExportMeasuresActionPerformed
    {//GEN-HEADEREND:event_butExportMeasuresActionPerformed
        String folder = new File(this.getSettings().getMeasuresXmlFile()).getParent();
        String fileName = folder + CommonDefs.fileSeparator + CommonDefs.defaultXlsFile;
        
        // Create modal window
        FileExportForm fef = new FileExportForm(this.measuresData.getBMR_Measures(), fileName);
        fef.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        fef.setTitle("Measures export");

        fef.setVisible(true);   // Parameters modification and data export       
    }//GEN-LAST:event_butExportMeasuresActionPerformed

    private void butWHOActionPerformed(ActionEvent evt)//GEN-FIRST:event_butWHOActionPerformed
    {//GEN-HEADEREND:event_butWHOActionPerformed
        WHO_ClassificationForm wcf = new WHO_ClassificationForm();
    
        wcf.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        wcf.setTitle("WHO Blood pressure classification");

        wcf.setVisible(true);   // Parameters modification and data export         
    }//GEN-LAST:event_butWHOActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(BMR_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(BMR_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(BMR_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(BMR_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                String fileName = "";
                if (args.length > 0)
                {
                    fileName = args[0];
                }
                new BMR_Main(fileName).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    JButton butAddMeasure;
    JButton butDeleteMeasure;
    JButton butEditMeasure;
    JToggleButton butExportMeasures;
    JButton butSettings;
    JButton butWHO;
    JDesktopPane desktop;
    JMenu fileMenu;
    JInternalFrame frameMeasuresList;
    JMenu jMenu2;
    JPanel measuresTablePanel;
    JPanel measuresToolsPanel;
    JMenuBar menuBar;
    JScrollPane paneMeasuresList;
    JTable tableMeasuresList;
    JPanel toolsPanel;
    // End of variables declaration//GEN-END:variables
}
