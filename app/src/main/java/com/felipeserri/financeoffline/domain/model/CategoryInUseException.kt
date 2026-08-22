package com.felipeserri.financeoffline.domain.model

class CategoryInUseException :
    Exception("Esta categoria possui transações vinculadas e não pode ser excluída")