import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-posts-statistic',
  templateUrl: './posts-statistic.component.html',
  styleUrls: ['./posts-statistic.component.css']
})
export class PostsStatisticComponent implements OnInit {
  progressPercentage: any;

  constructor( private router: Router,
    private announcementService: PostService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.getProjectProgress();
  }

  getProjectProgress(): void {
    this.announcementService.getPostsProgression().toPromise().then((res: any) => {
      this.progressPercentage = res;
    });
  }

 

}
