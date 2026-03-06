package lt.viko.eif.kskrebe.candlebusiness.config;

import lt.viko.eif.kskrebe.candlebusiness.model.Address;
import lt.viko.eif.kskrebe.candlebusiness.model.Contract;
import lt.viko.eif.kskrebe.candlebusiness.model.CustomerOrder;
import lt.viko.eif.kskrebe.candlebusiness.model.CustomerOrderItem;
import lt.viko.eif.kskrebe.candlebusiness.model.Expense;
import lt.viko.eif.kskrebe.candlebusiness.model.ExpenseCategory;
import lt.viko.eif.kskrebe.candlebusiness.model.Ingredient;
import lt.viko.eif.kskrebe.candlebusiness.model.IngredientType;
import lt.viko.eif.kskrebe.candlebusiness.model.OrderStatus;
import lt.viko.eif.kskrebe.candlebusiness.model.OrderType;
import lt.viko.eif.kskrebe.candlebusiness.model.Product;
import lt.viko.eif.kskrebe.candlebusiness.model.ProductionTask;
import lt.viko.eif.kskrebe.candlebusiness.model.ProductionType;
import lt.viko.eif.kskrebe.candlebusiness.model.Supplier;
import lt.viko.eif.kskrebe.candlebusiness.model.User;
import lt.viko.eif.kskrebe.candlebusiness.model.UserRole;
import lt.viko.eif.kskrebe.candlebusiness.repository.AddressRepository;
import lt.viko.eif.kskrebe.candlebusiness.repository.ContractRepository;
import lt.viko.eif.kskrebe.candlebusiness.repository.CustomerOrderRepository;
import lt.viko.eif.kskrebe.candlebusiness.repository.ExpenseRepository;
import lt.viko.eif.kskrebe.candlebusiness.repository.IngredientRepository;
import lt.viko.eif.kskrebe.candlebusiness.repository.ProductRepository;
import lt.viko.eif.kskrebe.candlebusiness.repository.ProductionTaskRepository;
import lt.viko.eif.kskrebe.candlebusiness.repository.SupplierRepository;
import lt.viko.eif.kskrebe.candlebusiness.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Uzpildo sistema demo duomenimis, jei DB tuscia.
 */
@Configuration
public class DemoDataSeeder {

    /**
     * Sukuria demo irasus pirmo paleidimo metu.
     */
    @Bean
    CommandLineRunner seedDemoData(UserRepository userRepository,
                                   AddressRepository addressRepository,
                                   ContractRepository contractRepository,
                                   IngredientRepository ingredientRepository,
                                   ProductRepository productRepository,
                                   CustomerOrderRepository customerOrderRepository,
                                   ExpenseRepository expenseRepository,
                                   ProductionTaskRepository productionTaskRepository,
                                   SupplierRepository supplierRepository) {
        return args -> {
            Contract contractA = contractRepository.findByContractNumber("T-2026-001")
                    .orElseGet(() -> contractRepository.save(new Contract(
                            "T-2026-001",
                            "Zaliavu tiekimo sutartis su menesiniu uzsakymu grafiku."
                    )));
            Contract contractB = contractRepository.findByContractNumber("T-2026-002")
                    .orElseGet(() -> contractRepository.save(new Contract(
                            "T-2026-002",
                            "Kvapiuju alieju ir dazikliu tiekimo sutartis."
                    )));

            Address kaunas = addressRepository.findAllByOrderByCountryAscStreetAscHouseNumberAsc().stream()
                    .filter(address -> "Lietuva".equals(address.getCountry()) && "Pramones g.".equals(address.getStreet()) && "12".equals(address.getHouseNumber()))
                    .findFirst()
                    .orElseGet(() -> addressRepository.save(new Address("Lietuva", "Pramones g.", "12", null)));
            Address vilnius = addressRepository.findAllByOrderByCountryAscStreetAscHouseNumberAsc().stream()
                    .filter(address -> "Lietuva".equals(address.getCountry()) && "Vilniaus g.".equals(address.getStreet()) && "8".equals(address.getHouseNumber()))
                    .findFirst()
                    .orElseGet(() -> addressRepository.save(new Address("Lietuva", "Vilniaus g.", "8", "2")));

            Supplier aromatika = supplierRepository.findByName("Aromatika")
                    .orElseGet(() -> supplierRepository.save(new Supplier("Aromatika")));
            aromatika.setAddress(vilnius);
            aromatika.setContract(contractB);
            aromatika = supplierRepository.save(aromatika);
            Supplier balticRaw = supplierRepository.findByName("Baltic Raw")
                    .orElseGet(() -> supplierRepository.save(new Supplier("Baltic Raw")));
            balticRaw.setAddress(kaunas);
            balticRaw.setContract(contractA);
            balticRaw = supplierRepository.save(balticRaw);

            if (userRepository.count() > 0 || productRepository.count() > 0) {
                return;
            }

            User vadovas = userRepository.save(new User("Asta", "Vadove", "asta@ambera.lt", "asta.vadove", UserRole.VADOVAS));
            User darbuotojas = userRepository.save(new User("Mantas", "Meistras", "mantas@ambera.lt", "mantas.darbuotojas", UserRole.DARBUOTOJAS));
            User finansininke = userRepository.save(new User("Ruta", "Skaiciuotoja", "ruta@ambera.lt", "ruta.finansai", UserRole.FINANSININKAS));
            User kliente = userRepository.save(new User("Egle", "Pirkeja", "egle@klientas.lt", "egle.kliente", UserRole.KLIENTAS));
            User klientas = userRepository.save(new User("Jonas", "Uzsakovas", "jonas@klientas.lt", "jonas.klientas", UserRole.KLIENTAS));

            Ingredient sojuVaskas = ingredientRepository.save(new Ingredient("Soju vaskas", IngredientType.NATURALUS));
            sojuVaskas.setSupplier(balticRaw);
            sojuVaskas = ingredientRepository.save(sojuVaskas);
            Ingredient biciuVaskas = ingredientRepository.save(new Ingredient("Biciu vaskas", IngredientType.NATURALUS));
            biciuVaskas.setSupplier(balticRaw);
            biciuVaskas = ingredientRepository.save(biciuVaskas);
            Ingredient levanduAliejus = ingredientRepository.save(new Ingredient("Levandu eterinis aliejus", IngredientType.NATURALUS));
            levanduAliejus.setSupplier(aromatika);
            levanduAliejus = ingredientRepository.save(levanduAliejus);
            Ingredient kvapiklis = ingredientRepository.save(new Ingredient("Vaniles kvapiklis", IngredientType.PRAMONINIS));
            kvapiklis.setSupplier(aromatika);
            kvapiklis = ingredientRepository.save(kvapiklis);
            Ingredient glicerinas = ingredientRepository.save(new Ingredient("Augalinis glicerinas", IngredientType.NATURALUS));
            glicerinas.setSupplier(balticRaw);
            glicerinas = ingredientRepository.save(glicerinas);
            Ingredient daziklis = ingredientRepository.save(new Ingredient("Muilo daziklis", IngredientType.PRAMONINIS));
            daziklis.setSupplier(aromatika);
            daziklis = ingredientRepository.save(daziklis);

            Product zvake = new Product("Gintarine zvake", "Ranku darbo zvake su siltu vaniles kvapu.", new BigDecimal("14.90"), 12, ProductionType.RANKU_DARBO, true);
            zvake.getIngredients().addAll(List.of(sojuVaskas, biciuVaskas, kvapiklis));
            productRepository.save(zvake);

            Product muilas = new Product("Levandu muilas", "Naturalus muilas su levandu aromatu.", new BigDecimal("7.50"), 20, ProductionType.RANKU_DARBO, false);
            muilas.getIngredients().addAll(List.of(glicerinas, levanduAliejus, daziklis));
            productRepository.save(muilas);

            Product dovanuRinkinys = new Product("Dovanu rinkinys", "Zvakes ir muilo komplektas dovanai.", new BigDecimal("24.90"), 8, ProductionType.MASINE_GAMYBA, true);
            dovanuRinkinys.getIngredients().addAll(List.of(sojuVaskas, glicerinas, kvapiklis));
            productRepository.save(dovanuRinkinys);

            CustomerOrder pirmasUzsakymas = new CustomerOrder(kliente, OrderType.GAMYBA_PAGAL_UZSAKYMA, OrderStatus.PARUOSTAS, LocalDateTime.now().minusDays(3), BigDecimal.ZERO);
            CustomerOrderItem pirmaEilute = new CustomerOrderItem(pirmasUzsakymas, zvake, 2, zvake.getPrice());
            CustomerOrderItem antraEilute = new CustomerOrderItem(pirmasUzsakymas, muilas, 3, muilas.getPrice());
            pirmasUzsakymas.setItems(List.of(pirmaEilute, antraEilute));
            pirmasUzsakymas.setTotalAmount(new BigDecimal("52.30"));
            customerOrderRepository.save(pirmasUzsakymas);

            CustomerOrder antrasUzsakymas = new CustomerOrder(klientas, OrderType.PIRKIMAS_IS_SANDELIO, OrderStatus.PARUOSTAS, LocalDateTime.now().minusDays(1), BigDecimal.ZERO);
            CustomerOrderItem treciaEilute = new CustomerOrderItem(antrasUzsakymas, dovanuRinkinys, 1, dovanuRinkinys.getPrice());
            antrasUzsakymas.setItems(List.of(treciaEilute));
            antrasUzsakymas.setTotalAmount(new BigDecimal("24.90"));
            customerOrderRepository.save(antrasUzsakymas);

            productionTaskRepository.save(new ProductionTask(
                    pirmaEilute,
                    darbuotojas,
                    kliente,
                    LocalDateTime.now().minusDays(2),
                    "Pagaminta, supakuota ir paruosta atsiemimui."
            ));

            expenseRepository.save(new Expense(
                    ExpenseCategory.INGREDIENTAI,
                    "Soju vasko ir eteriniu alieju pirkimas",
                    new BigDecimal("68.40"),
                    LocalDate.now().minusDays(5),
                    finansininke
            ));

            expenseRepository.save(new Expense(
                    ExpenseCategory.PAKAVIMAS,
                    "Dezutes, juosteles ir etiketes",
                    new BigDecimal("23.90"),
                    LocalDate.now().minusDays(2),
                    finansininke
            ));
        };
    }
}
