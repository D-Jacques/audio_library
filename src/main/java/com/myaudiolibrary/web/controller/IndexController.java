package com.myaudiolibrary.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @RequestMapping(method = RequestMethod.GET)
    public String accueil(final ModelMap modelMain){
        return "accueil";
    }
}
/*
<!--<h2>Détail de l'artiste [[${Artist.name}]]</h2>
<form id="saveForm" th:action="'/artists/registerArtist'" method="post" class="row">

	<div class="row">
		<div class="col-lg-12">
			<div class="form-group">
				<label for="name" class="form-control-label">Nom</label>
				<input type="hidden" name="id" th:value="${Artist.id}">
				<input type="text" name="name" id="name" class="ember-text-field form-control ember-view" th:value="${Artist.name}">

				<label for="performance" class="form-control-label">Albums</label>
				<div class="row" th:each="Album : ${Artist.albums}">
					<div class="col-lg-10">
						<div class="list-group">
							<li class="list-group-item">&nbsp;[[${Album.title}]]</li>
						</div>
					</div>
					<div class="col-lg-2 text-center">
						<div class="list-group text-center">
							<button class="btn-danger list-group-item list-group-item-action"><span class="glyphicon glyphicon-remove"></span></button>
						</div>
					</div>
				</div>
				<div class="row">
					<form id="saveAlbum" th:action="'/albums/registerAlbum'" method="post">
						<div class="col-lg-10">
							<input name="id" type="hidden" th:value="${albumToCreate.id}">
							<input name="title" type="text" placeholder="Ajouter un album..." class="form-control ember-text-field ember-view" th:value="${albumToCreate.title}">
						</div>
					</form>
					<div class="col-lg-2 text-center">
						<button form="saveAlbum" type="submit" class="btn-success list-group-item list-group-item-action"><span class="glyphicon glyphicon-plus"></span></button>
					</div>
				</div>
			</div>
		</div>
	</div>

</form>-->
*/