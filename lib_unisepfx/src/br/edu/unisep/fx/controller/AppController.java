package br.edu.unisep.fx.controller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import br.edu.unisep.fx.annotation.ColValueMap;
import br.edu.unisep.fx.annotation.FXColumn;
import br.edu.unisep.fx.annotation.IntToString;
import br.edu.unisep.fx.annotation.OnlyNumber;
import br.edu.unisep.fx.annotation.Required;
import br.edu.unisep.fx.events.KeyUtils;
import br.edu.unisep.fx.events.OnModalClose;
import br.edu.unisep.fx.message.AlertUtils;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class AppController implements Initializable {

	protected URL location;
	protected ResourceBundle resources;
	
	@SuppressWarnings("unchecked")
	protected void configColumns() {

		for (Field f : getClass().getDeclaredFields()) {
			if (f.isAnnotationPresent(FXColumn.class)) {
				try {
					f.setAccessible(true);
					
					FXColumn fxb = f.getAnnotation(FXColumn.class);
					
					TableColumn<?, ?> col = (TableColumn<?, ?>) f.get(this);
					
					double w = fxb.percentWidth();
					
					if (w != -1) {
						col.setMaxWidth(Integer.MAX_VALUE * w);
					}
					
					if (f.isAnnotationPresent(ColValueMap.class)) {
						
						ColValueMap cv = f.getAnnotation(ColValueMap.class);
						
						IntToString[] cvs = cv.value();
						
						Map<Integer, String> mValues = Arrays.asList(cvs).
								stream().collect(Collectors.toMap(IntToString::from, IntToString::to));
						
						TableColumn<Object, String> isCol = (TableColumn<Object, String>) col;
						isCol.setCellValueFactory((param) -> {
							try {
								Integer v = (Integer) getBeanValue(param.getValue(), fxb.property());
								String value = mValues.get(v);
								return new SimpleStringProperty(value);
							} catch (Exception e) {
								e.printStackTrace();
								throw new RuntimeException("Erro ao configurar coluna! Propriedade: " + fxb.property());
							}
						}); 

						isCol.setCellFactory(column -> {
						    return new TableCell<Object, String>() {
						        @Override
						        protected void updateItem(String item, boolean empty) {
						            super.updateItem(item, empty);

						            if (item == null || empty) {
						                setText(null);
						            } else {
						                setText(item);
						                setAlignment(fxb.align());
						            }
						        }
						    };
						});
					} else {
					
						if (!fxb.dateFormat().equals("")) {
							TableColumn<Object, LocalDate> dtCol = (TableColumn<Object, LocalDate>) col;
							dtCol.setCellValueFactory((param) -> {
								try {
									Object v = getBeanValue(param.getValue(), fxb.property());
									LocalDate value = null;
									if (v instanceof Property<?>) {
										value = ((SimpleObjectProperty<LocalDate>) v).get(); 
									} else {
										value = (LocalDate) v;	
									}
									
									return new SimpleObjectProperty<LocalDate>(value);
								} catch (Exception e) {
									e.printStackTrace();
									throw new RuntimeException("Erro ao configurar coluna! Propriedade: " + fxb.property());
								}
							}); 
	
							dtCol.setCellFactory(column -> {
							    return new TableCell<Object, LocalDate>() {
							        @Override
							        protected void updateItem(LocalDate item, boolean empty) {
							            super.updateItem(item, empty);
	
							            DateTimeFormatter fmt = DateTimeFormatter.ofPattern(fxb.dateFormat());
							            
							            if (item == null || empty) {
							                setText(null);
							            } else {
							                setText(fmt.format(item));
							                setAlignment(fxb.align());
							            }
							            
							        }
							    };
							});
						} else if (!fxb.numberFormat().equals("")){
							
							TableColumn<Object, Number> nrCol = (TableColumn<Object, Number>) col;
							nrCol.setCellValueFactory((param) -> {
								try {
									Object v = getBeanValue(param.getValue(), fxb.property());
									Number value = null;
									
									if (v instanceof SimpleIntegerProperty) {
										value = ((SimpleIntegerProperty) v).getValue();
									} else if (v instanceof SimpleDoubleProperty) {
										value = ((SimpleDoubleProperty) v).getValue();
									} else {
										value = (Number) v;
									}
									 
									return new SimpleObjectProperty<Number>(value);
								} catch (Exception e) {
									e.printStackTrace();
									throw new RuntimeException("Erro ao configurar coluna! Propriedade: " + fxb.property());
								}
							});
							
							nrCol.setCellFactory(column -> {
							    return new TableCell<Object, Number>() {
							        @Override
							        protected void updateItem(Number item, boolean empty) {
							            super.updateItem(item, empty);
	
							            DecimalFormat df = new DecimalFormat(fxb.numberFormat());
							            
							            if (item == null || empty) {
							                setText(null);
							            } else {
							                setText(df.format(item));
							                setAlignment(fxb.align());
							            }
							        }
							    };
							});
						} else {
							TableColumn<Object, String> strCol = (TableColumn<Object, String>) col;
							strCol.setCellValueFactory((param) -> {
								try {
									Object value = getBeanValue(param.getValue(), fxb.property());
									
									if (value != null) {
										
										if (value instanceof SimpleStringProperty) {
											return (SimpleStringProperty) value;
										} else {
											return new SimpleStringProperty(value.toString());	
										}
										
									} else {
										return null;
									}
									
								} catch (Exception e) {
									e.printStackTrace();
									throw new RuntimeException("Erro ao configurar coluna! Propriedade: " + fxb.property());
								}
							});
							
							strCol.setCellFactory(column -> {
							    return new TableCell<Object, String>() {
							        @Override
							        protected void updateItem(String item, boolean empty) {
							            super.updateItem(item, empty);
	
							            if (item == null || empty) {
							                setText(null);
							            } else {
							                setText(item);
							                setAlignment(fxb.align());
							            }
							        }
							    };
							});
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("Não foi possível configurar as colunas! " + e.getMessage());
				}
			}
		}
	}
	
	protected void configInputNumber() {
		try {
			Field[] flds = this.getClass().getDeclaredFields();
			
			for (Field f : flds) {
				f.setAccessible(true);
				
				Object obj = f.get(this);
				
				if (obj instanceof TextField && f.isAnnotationPresent(OnlyNumber.class)) {
					OnlyNumber n = f.getAnnotation(OnlyNumber.class);
					TextField txt = (TextField) obj;
					
					if (n.money()) {
						txt.setOnKeyTyped(KeyUtils::checkMoney);
					} else if (n.isDecimal()) {
						
						if (n.allowsNegative()) {
							txt.setOnKeyTyped( (e) -> {
								KeyUtils.checkDecimal(e, n.decimalCount());
							});
						} else {
							txt.setOnKeyTyped( (e) -> {
								KeyUtils.checkDecimalPositive(e, n.decimalCount());
							});
						}
					} else {
						if (n.allowsNegative()) {
							txt.setOnKeyTyped(KeyUtils::checkInteger);
						} else {
							txt.setOnKeyTyped(KeyUtils::checkIntegerPositive);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Não foi possível executar a configuração OnlyNumber! " + e.getMessage());
		}
	}
	
	private Object getBeanValue(Object src, String property) throws Exception {
		
		String[] path = property.split("\\.");
		
		Object obj = src;
		
		for (int i = 0; i < path.length; i++) {
			String prop = path[i];
			
			Field f = obj.getClass().getDeclaredField(prop);
			f.setAccessible(true);
			
			obj = f.get(obj);
		}
		
		return obj;
	}
	
	public void openModal(String fxml, Object... params) {
		this.openModal(fxml, null, params);
	}
	
	public void openModal(String fxml, OnModalClose onClose, Object... params) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
		
		try {
			Pane root = (Pane) loader.load();
			
			ModalController ctrl = (ModalController) loader.getController();
			ctrl.parentController = this;
			ctrl.window = stage;
			ctrl.params = params;
		
			if (onClose != null) {
				ctrl.onClose = onClose;
			}
			
			ctrl.onModalInit();
			
			Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
			stage.setScene(scene);
			stage.setResizable(false);
			stage.initStyle(StageStyle.UTILITY);
			stage.initModality(Modality.APPLICATION_MODAL);
			
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
			AlertUtils.exibirErro("Erro ao abrir modal: " + e.getMessage());
		}
	}
	
	public void openScene(AnchorPane parent, String fxml) {
		try {
			Pane tela = FXMLLoader.load(getClass().getResource(fxml));
			
			tela.setPrefWidth(parent.getPrefWidth());
			tela.setPrefHeight(parent.getPrefHeight());
			
			AnchorPane.setTopAnchor(tela, 0d);
			AnchorPane.setRightAnchor(tela, 0d);
			AnchorPane.setLeftAnchor(tela, 0d);
			AnchorPane.setBottomAnchor(tela, 0d);
			
			parent.getChildren().clear();
			parent.getChildren().add(tela);
		} catch (IOException e) {
			e.printStackTrace();
			AlertUtils.exibirErro("Não foi possível carregar o FXML!");
		}
	}
	
	protected boolean validate() {
		
		try {
			for (Field f : getClass().getDeclaredFields()) {
				if (f.isAnnotationPresent(Required.class)) {
					
					Required req = f.getAnnotation(Required.class);
					f.setAccessible(true);
					
					Object comp = (Object) f.get(this);
					
					if (comp instanceof TextInputControl) {
						TextInputControl txt = (TextInputControl) comp;
						if (txt != null && txt.getText().trim().equals("")) {
							AlertUtils.exibirErro("Campo " + req.campo() + " é obrigatório!");
							txt.requestFocus();
							return false;
						}
					} else if (comp instanceof ChoiceBox) {
						ChoiceBox<?> cmb = (ChoiceBox<?>) comp;
						if (cmb != null && cmb.getValue() == null) {
							AlertUtils.exibirErro("Campo " + req.campo() + " é obrigatório!");
							cmb.requestFocus();
							return false;
						}
					} else if (comp instanceof ComboBoxBase) {
						ComboBoxBase<?> cmb = (ComboBoxBase<?>) comp;
						if (cmb != null && cmb.getValue() == null) {
							AlertUtils.exibirErro("Campo " + req.campo() + " é obrigatório!");
							cmb.requestFocus();
							return false;
						}
					}
					
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	protected void clearAllFields() {
		for (Field f : getClass().getDeclaredFields()) {
			f.setAccessible(true);
			try {
				Object obj = f.get(this);

				if (obj instanceof TextInputControl) {
					((TextInputControl) obj).clear();
				} else if (obj instanceof ComboBoxBase) {
					((ComboBoxBase) obj).setValue(null);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.location = location;
		this.resources = resources;
		
		this.configColumns();
		this.configInputNumber();
		
		this.onInit();
	}
	
	protected abstract void onInit();

}