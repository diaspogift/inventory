package com.dddtraining.inventory;

import com.dddtraining.inventory.application.command.RegisterProductCommand;
import com.dddtraining.inventory.domain.model.product.Product;
import com.dddtraining.inventory.domain.model.product.ProductId;
import com.dddtraining.inventory.domain.model.product.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.dddtraining.inventory.application.command.CreateStockCommand;
import com.dddtraining.inventory.application.product.ProductApplicationService;
import com.dddtraining.inventory.application.stock.StockApplicationService;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.SQLException;

@SpringBootApplication
public class InventoryApplication {

    private static final Logger logger = LoggerFactory
            .getLogger(InventoryApplication.class);

    public static void main(String[] args) {




		ConfigurableApplicationContext container = SpringApplication.run(InventoryApplication.class, args);

        ProductApplicationService productApplicationService = container.getBean(ProductApplicationService.class);
        ProductRepository productRepository = container.getBean(ProductRepository.class);


        productRepository.add(new Product(
                productRepository.nextIdentity(),
                "Name",
                "DESCRIPTION"
        ));

    }
}
