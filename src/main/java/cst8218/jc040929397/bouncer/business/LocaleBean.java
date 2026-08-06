/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cst8218.jc040929397.bouncer.business;

/**
 *
 * @author Ethan Baas
 */

import jakarta.enterprise.context.SessionScoped; // Or javax.enterprise.context.SessionScoped
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;                    // Or javax.inject.Named
import java.io.Serializable;
import java.util.Locale;

@Named(value = "localeBean") // Explicitly names the bean for XHTML
@SessionScoped
public class LocaleBean implements Serializable {
    
    // 1. Initialize with a safe default locale instead of pulling from getViewRoot() immediately
    private Locale locale = Locale.ENGLISH; 

    public Locale getLocale() {
        // 2. Safe lazy fallback: check if a view root exists before overriding
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            UIViewRoot viewRoot = context.getViewRoot();
            if (viewRoot != null && locale == null) {
                locale = viewRoot.getLocale();
            }
        }
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String changeLocale(String languageCode) {
        this.locale = new Locale(languageCode);
        
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null && context.getViewRoot() != null) {
            context.getViewRoot().setLocale(this.locale);
        }
        
        // Force page redirect to re-render the view completely with the new locale
        return context.getViewRoot().getViewId() + "?faces-redirect=true";
    }
}