package com.nhnacademy.edu.config;

import com.nhnacademy.edu.Base;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

@ComponentScan(basePackageClasses = Base.class, excludeFilters = {@ComponentScan.Filter(Controller.class)})
public class RootConfig {
}
