/**
 * JBoss, Home of Professional Open Source.
 * Copyright 2014-2019 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.pnc.facade.mapper.api;

import org.jboss.pnc.dto.ProductRef;
import org.jboss.pnc.dto.ProductVersionRef;
import org.jboss.pnc.model.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Jan Michalov <jmichalo@redhat.com>
 */
@Mapper(config = MapperCentralConfig.class, uses = {ProductVersionMapper.class})
public interface ProductMapper extends EntityMapper<Product, org.jboss.pnc.dto.Product,ProductRef> {
    @Override
    Product toEntity(org.jboss.pnc.dto.Product dtoEntity);

    @Override
    default Product toIDEntity(ProductRef dtoEntity) {
        Product product = new Product();
        product.setId(dtoEntity.getId());
        return product;
    };

    @Override
    @Mapping(target = "productVersions", resultType = ProductVersionRef.class)
    org.jboss.pnc.dto.Product toDTO(Product dbEntity);

    @Override
    @BeanMapping(ignoreUnmappedSourceProperties = {"productVersions"})
    ProductRef toRef(Product dbEntity);
}
