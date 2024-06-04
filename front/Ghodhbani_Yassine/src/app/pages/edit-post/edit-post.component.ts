import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-edit-post',
  templateUrl: './edit-post.component.html',
  styleUrls: ['./edit-post.component.css']
})
export class EditPostComponent implements OnInit {
  postId: any;
  success: string = '';
  error: string = '';
  loading: boolean = false;
  announcement: any | null = null;

  form = new FormGroup({
    title: new FormControl('', Validators.required),
    content: new FormControl('', Validators.required),
    location: new FormControl('', Validators.required),
    posteDate: new FormControl(new Date(), Validators.required)
  });

  constructor(
    private router: Router,
    private announcementService: PostService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.postId = this.route.snapshot.params['id'];
    this.loadPost();
  }

  loadPost() {
    this.announcementService.getAnnouncementById(this.postId).toPromise().then((res: any) => {
      this.announcement = res;
      this.form.patchValue({
        title: res.title,
        content: res.content,
        location: res.location,
        posteDate: new Date(res.posteDate)
      });
    }).catch((err: HttpErrorResponse) => {
      this.error = 'Failed to load post data';
      console.error(err);
    });
  }

  updateAnnouncement() {
    this.error = '';
    this.loading = true;

    if (!this.announcement) {
      this.error = 'Announcement data is not loaded';
      this.loading = false;
      return;
    }

    const updatedAnnouncement = {
      ...this.announcement,
      ...this.form.value,
      posteDate: this.form.value.posteDate || this.announcement.posteDate
    };

    this.announcementService.updateAnnouncement(this.postId, updatedAnnouncement).toPromise().then((res: any) => {
      this.success = res.message;
      this.loading = false;
      this.router.navigateByUrl('/posts');
    }).catch((err: HttpErrorResponse) => {
      if (err.status === 500) {
        this.error = 'Internal Server Error';
      }
      this.loading = false;
    });
  }
}