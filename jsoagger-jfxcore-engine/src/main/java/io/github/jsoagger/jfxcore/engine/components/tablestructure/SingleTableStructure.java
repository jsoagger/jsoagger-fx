/**
 *
 */
package io.github.jsoagger.jfxcore.engine.components.tablestructure;

import java.util.List;

import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.jfxcore.api.IBuildable;
import io.github.jsoagger.jfxcore.api.IJSoaggerController;
import io.github.jsoagger.jfxcore.api.IPaginatedDataProvider;
import io.github.jsoagger.jfxcore.api.IPaginatedDataProvider.Direction;
import io.github.jsoagger.jfxcore.api.services.Services;
import io.github.jsoagger.jfxcore.viewdef.json.xml.model.VLViewComponentXML;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.css.PseudoClass;

public abstract class SingleTableStructure extends AbstractTableStructure {

  protected SimpleObjectProperty<TableStructureState> tableState = new SimpleObjectProperty<>();

  protected PseudoClass nodata = PseudoClass.getPseudoClass("nodata");
  private final LoadDataTask loadFirstPage = new LoadDataTask();


  /*-----------------------------------------------------------------------------
  | Configurations
   *=============================================================================*/
  /** Current displayed and selected items in the table */
  protected ObservableList<OperationData> items = FXCollections.observableArrayList();
  protected FilteredList<OperationData> filteredList = new FilteredList<>(items, data -> true);
  protected IPaginatedDataProvider dataProvider;

  /**
   * @{inheritedDoc}
   */
  @Override
  public void buildFrom(IJSoaggerController controller, VLViewComponentXML configuration) {
    tableState.set(TableStructureState.BUILDING);
    super.buildFrom(controller, configuration);
    tableState.set(TableStructureState.BUILDED);
  }

  @Override
  public List<IBuildable> getDefaultActions() {
    return defaultActions;
  }



  @Override
  public void forceLoadFirstPage() {
    if (Platform.isFxApplicationThread()) {
      loadFirstPage.restart();
    } else {
      Platform.runLater(() -> {
        loadFirstPage.restart();
      });
    }
  }


  @Override
  protected void loadFirstPage() {
    if (contentConfiguration != null) {
      final boolean loadFirstPage = contentConfiguration.getBooleanProperty("loadFirstPage", true);
      if (loadFirstPage) {
        forceLoadFirstPage();
      }
    }
  }

  @Override
  public void refreshCurrentPage() {
    super.refreshCurrentPage();
    if (childTree().isEmpty()) {
      dataProvider.navigate(controller, model.get(), Direction.CURRENT, e -> {
        model.set(null);
        model.set(e);
      });
    }
    // reload last item in tree
    else {

    }
  }

  @Override
  public void nextPage(SimpleObjectProperty<MultipleResult> model) {
    dataProvider.navigate(controller, model.get(), Direction.NEXT, e -> this.model.set(e));
  }


  /**
   * @{inheritedDoc}
   */
  @Override
  public void previousPage(SimpleObjectProperty<MultipleResult> model) {
    dataProvider.navigate(controller, model.get(), Direction.PREVIOUS, e -> this.model.set(e));
  }


  @Override
  public void firstPage(SimpleObjectProperty<MultipleResult> model) {
    initialQuery = model.get();
    dataProvider.navigate(controller, model.get(), Direction.FIRST, e -> this.model.set(e));
  }


  @Override
  public void lastPage(SimpleObjectProperty<MultipleResult> model) {
    dataProvider.navigate(controller, model.get(), Direction.LAST, e -> this.model.set(e));
  }


  public OperationData getFirstItem() {
    if (items.size() > 0) {
      return items.get(0);
    }

    return null;
  }

  @Override
  public void deleteSelectedRows() {
    for (final Object selection : selections) {
      // TableRowSelectPresenter presenter = (TableRowSelectPresenter)
      // selection;
      // Object item = ((EpTableCell)
      // presenter.getCell()).getTableRow().getItem();
      items.remove(selection);
    }

    selections.clear();

    if (items.size() == 0) {
      setNoContent();
    }
  }


  public IPaginatedDataProvider getDataProvider() {
    return dataProvider;
  }


  @Override
  public ObservableList<OperationData> getItems() {
    return items;
  }


  @Override
  public void onModelUpdated(IOperationResult newValue) {

    System.out.println("SingleTableStructure On model updated :  " + newValue);
    System.out.println(
        "SingleTableStructure On model updated :  " + ((MultipleResult) newValue).getData().size());

    if (newValue == null || ((MultipleResult) newValue).getData().size() < 1) {
      items.clear();
      elementsCount.set(0);

      if (newValue != null && pagination != null) {
        pagination.setModel((MultipleResult) newValue);
      }

      if (pagination != null) {
        if (!pagination.getDisplay().visibleProperty().isBound())
          pagination.getDisplay().setVisible(false);
      }

      if (Platform.isFxApplicationThread()) {
        setNoContent();
      } else {
        Platform.runLater(() -> setNoContent());
      }

      // ((StandardViewController)controller).selectedElementProperty().set(null);
    } else {

      System.out.println("[DEBUG] On model updated : >>>>>>>>> 22222222222222");

      final MultipleResult multipleResult = (MultipleResult) newValue;
      elementsCount.set(multipleResult.totaElements());
      if (pagination != null) {
        pagination.getDisplay().pseudoClassStateChanged(nodata, false);
        if (!pagination.getDisplay().visibleProperty().isBound())
          pagination.getDisplay().setVisible(true);
        pagination.setModel(multipleResult);
      }

      // force select first element
      // ((StandardViewController)controller).selectedElementProperty().set(multipleResult.getData().get(0));
      System.out.println("[DEBUG] On model updated : >>>>>>>>> 33");

      if (Platform.isFxApplicationThread()) {
        setData(multipleResult);
      } else {
        Platform.runLater(() -> {
          setData(multipleResult);
        });
      }
    }
  }

  @Override
  public void buildContent() {
    if (contentConfiguration != null) {
      final String dataProviderBean = contentConfiguration.getPropertyValue("dataLoader");

      if (StringUtils.isNotBlank(dataProviderBean)) {
        dataProvider = (IPaginatedDataProvider) Services.getBean(dataProviderBean);
        dataProvider.initFromConfiguration(controller, contentConfiguration);
      }
    }
  }

  private class LoadDataTask extends Service<Void> {

    @Override
    protected Task<Void> createTask() {

      final Task<Void> task = new Task<Void>() {

        @Override
        protected Void call() throws Exception {
          if (contentConfiguration != null) {

            if (loadFirstPageData) {
              getItems().clear();
              if (dataProvider != null) {
                setLoading();

                // @formatter:off
                final MultipleResult zero = new MultipleResult.Builder()
                    .addMeta(IOperationResult.pageSize,
                        pagination != null ? pagination.getCurrentPageSize() : 5)
                    .addMeta(IOperationResult.pageNumber, -1).build();
                // @formatter:on

                dataProvider.navigate(controller, zero, Direction.NEXT, result -> {
                  model.set(null);

                  System.out.println(">>>>>>>>> LoadDataTask result: " + result);
                  model.set(result);
                });
              } else {
                System.out.println(">>>>>>> LoadDataTask no content");
                setNoContent();
              }
            } else {
              setNoContent();
              System.out.println(">>>>>>> LoadDataTask no content 2222222");
            }
          }

          return null;
        }
      };

      setOnFailed(f -> {
        setNoContent();
      });

      setOnRunning(r -> {
        setLoading();
      });

      return task;
    }
  }

  @Override
  public FilteredList<OperationData> getFilteredDatas() {
    return filteredList;
  }
}
