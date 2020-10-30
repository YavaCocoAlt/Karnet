package net.harmal.karnet2.core.registers;

import net.harmal.karnet2.core.Order;
import net.harmal.karnet2.core.Product;
import net.harmal.karnet2.core.ProductCategory;
import net.harmal.karnet2.core.Stack;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.ArrayList;

public class ProductRegister
{

    public static int productIdCount = 0;

    private static List<Product> productRegister;

    // Returns PID
    public static int add(int unitPrice, @NotNull String name,
                          @NotNull List<ProductCategory> catIng  , @NotNull List<ProductCategory> catTaste,
                          @NotNull List<ProductCategory> catShape, @NotNull List<ProductCategory> catExtra)
    {
        return add(productIdCount++, unitPrice, name, catIng, catTaste, catShape, catExtra);
    }
    // Returns PID
    public static int add(int pid, int unitPrice, @NotNull String name,
                          @NotNull List<ProductCategory> catIng  , @NotNull List<ProductCategory> catTaste,
                          @NotNull List<ProductCategory> catShape, @NotNull List<ProductCategory> catExtra)
    {
        if(productRegister == null)
            productRegister = new ArrayList<Product>();
        if(pid < 0) // PID must be positive
        {
            return add(0, unitPrice, name, catIng, catTaste, catShape, catExtra);
        }
        for(Product p : productRegister) // Make sure the CID is unique
            if(p.pid() == pid)
            {
                return add(pid + 1, unitPrice, name, catIng, catTaste, catShape, catExtra);
            }
        productRegister.add(new Product(pid,  unitPrice, name, catIng, catTaste, catShape, catExtra));
        return pid;
    }

    public static void remove(int pid)
    {
        if(productRegister == null)
            productRegister = new ArrayList<>();

        for(Product p : productRegister)
            if(p.pid() == pid)
            {
                productRegister.remove(p);
                break;
            }
        for(Order o : OrderRegister.get())
        {
            for(Stack s : o.stacks())
                if(s.pid() == pid)
                {
                    o.stacks().remove(s);
                }
            if(o.stacks().size() == 0)
                OrderRegister.get().remove(o);
        }
    }

    @NotNull
    public static List<Product> get()
    {
        if(productRegister == null)
            productRegister = new ArrayList<>();
        return productRegister;
    }

    @NotNull
    public static List<ProductCategory> ingredientCategories()
    {
        List<ProductCategory> ing = new ArrayList<>();
        for(Product p : productRegister)
            for(ProductCategory c : p.categoryIngredient())
            {
                boolean exists = false;
                for(ProductCategory i : ing)
                    if(c.equals(i))
                    {
                        exists = true;
                        break;
                    }
                if(!exists)
                    ing.add(c);
            }
        return ing;
    }


}
